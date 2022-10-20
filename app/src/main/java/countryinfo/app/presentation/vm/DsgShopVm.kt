package countryinfo.app.presentation.vm

import android.location.Geocoder
import android.location.Location
import android.os.Build
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import countryinfo.app.data.model.DsgSearchResult
import countryinfo.app.data.model.ProductVOs
import countryinfo.app.data.repository.DsgSearchRepo
import countryinfo.app.di.IoDispatcher
import countryinfo.app.utils.ApiResult
import countryinfo.app.utils.CONSTANT_STRING_USA
import countryinfo.app.utils.ConvertSpeechToTextHelper
import countryinfo.app.utils.EMPTY_STRING
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import javax.inject.Inject

@HiltViewModel
class DsgShopVm @Inject constructor(
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
        viewModelScope.launch {
            withContext(dispatcher) {
                dsgSearchRepo.getSearchData(searchQuery, pageSize)
                    .catch {
                        errorSate.value = it.message.toString()
                    }
                    .collect {
                        when (it) {
                            is ApiResult.Success<DsgSearchResult> -> {
                                // Log.d("Search List", Gson().toJson(it.value))
                                val list = it.value.productVOs
                                list.sortByDescending {
                                    it.ratingValue
                                }

                                Log.d("Search List", Gson().toJson(list))
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
        var searchFor = ""
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
}