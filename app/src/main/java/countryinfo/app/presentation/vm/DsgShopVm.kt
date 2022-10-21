package countryinfo.app.presentation.vm

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.location.Geocoder
import android.location.Location
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import countryinfo.app.data.model.DsgSearchResult
import countryinfo.app.data.model.ProductVOs
import countryinfo.app.data.repository.DsgSearchRepo
import countryinfo.app.di.IoDispatcher
import countryinfo.app.utils.*
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import javax.inject.Inject

@Suppress("DEPRECATION")
@HiltViewModel
class DsgShopVm @Inject constructor(
    @ApplicationContext private val context: Context,
    private val dsgSearchRepo: DsgSearchRepo,
    private val geocoder: Geocoder,
    private val speechToTextHelper: ConvertSpeechToTextHelper,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    var selectedCount = mutableStateOf("5")

    private var textChangedJob: Job? = null

    private val errorSate: MutableStateFlow<String> = MutableStateFlow(
        EMPTY_STRING
    )

    val showShimmer = mutableStateOf(false)

    fun observeErrorState(): StateFlow<String> {
        return errorSate
    }

    private val dsgListState: MutableStateFlow<List<ProductVOs>> =
        MutableStateFlow(emptyList())

    fun observeDsgList(): StateFlow<List<ProductVOs>> {
        return dsgListState
    }

    private var _searchQuery = MutableStateFlow(EMPTY_STRING)
    fun searchQuery(): StateFlow<String> {
        return _searchQuery
    }

    private val isAmericaStateFlow: MutableStateFlow<Boolean> = MutableStateFlow(false)
    fun observeIsAmerica(): StateFlow<Boolean> {
        return isAmericaStateFlow
    }

    fun clearSearch() {
        dsgListState.value = emptyList()
        textChangedJob?.cancel()
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun search(searchQuery: String, pageSize: String) {
        showShimmer.value = true
        viewModelScope.launch {
            withContext(dispatcher) {
                dsgSearchRepo.getSearchData(searchQuery, pageSize)
                    .catch {
                        errorSate.value = it.message.toString()
                    }
                    .collect {
                        showShimmer.value = false
                        when (it) {
                            is ApiResult.Success<DsgSearchResult> -> {
                                val list = it.value.productVOs
                                list.sortByDescending { productVo ->
                                    productVo.ratingValue
                                }
                                dsgListState.value = list
                            }
                            is ApiResult.Failure -> {
                                it.message?.let { error ->
                                    errorSate.value = error
                                }
                            }
                            else -> {
                            }
                        }

                    }
            }
        }
    }

    fun searchByDebounce(query: String, count: String) {
        var searchFor = EMPTY_STRING
        if (query.length > 1) {
            val searchText = query.trim()
            if (searchText != searchFor) {
                searchFor = searchText

                textChangedJob?.cancel()
                textChangedJob = viewModelScope.launch(dispatcher) {
                    delay(700L)
                    if (searchText == searchFor) {
                        search(searchText, count)
                    }
                }
            }
        }
    }

    fun getCountryByLocation(location: Location) {

        viewModelScope.launch {
            withContext(dispatcher) {
                if (Build.VERSION.SDK_INT >= 33) {
                    geocoder.getFromLocation(
                        location.latitude,
                        location.longitude,
                        1
                    ) { addresses ->
                        val country = addresses[0].countryName
                        isAmericaStateFlow.value = country.equals(CONSTANT_STRING_USA)
                    }
                } else {
                    val addressList = geocoder.getFromLocation(
                        location.latitude,
                        location.longitude,
                        1
                    )
                    if ((addressList != null && addressList.size > 0)) {
                        val country = addressList[0]?.countryName
                        isAmericaStateFlow.value = country.equals(CONSTANT_STRING_USA)
                    }
                }
            }
        }

    }

    fun convertSpeechToText() {
        viewModelScope.launch {
            speechToTextHelper.speechToTextConverter(
                {
                    updateSearchQuery(it)
                }) {
                speechToTextHelper.speechRecognizer.stopListening()
            }
        }
    }

    fun getStringData(photoUri: Uri?) {
        viewModelScope.launch {
            withContext(dispatcher) {
                photoUri?.let {
                    var bitmap: Bitmap? = null

                    if (Build.VERSION.SDK_INT < 28) {
                        bitmap = MediaStore.Images.Media.getBitmap(context.contentResolver, it)
                    } else {
                        val source = ImageDecoder.createSource(context.contentResolver, it)
                        bitmap = ImageDecoder.decodeBitmap(
                            source
                        ) { decoder, _, _ ->
                            decoder.allocator = ImageDecoder.ALLOCATOR_SOFTWARE
                            decoder.isMutableRequired = true
                        }
                    }
                    bitmap?.let {
                        val scaledBitmap = Bitmap.createScaledBitmap(
                            it,
                            TensorFLowHelper.imageSize,
                            TensorFLowHelper.imageSize, false
                        )
                        TensorFLowHelper.classifyImage(scaledBitmap, context) {
                            _searchQuery.value = it
                        }
                    }

                }
            }

        }
    }
}