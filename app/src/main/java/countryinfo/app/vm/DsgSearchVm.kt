package countryinfo.app.vm

import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Build
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import countryinfo.app.di.hiltmodules.IoDispatcher
import countryinfo.app.repo.DsgSearchRepo
import countryinfo.app.utils.ApiResult
import countryinfo.app.utils.CONSTANT_STRING_USA
import countryinfo.app.utils.EMPTY_STRING
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import javax.inject.Inject

@HiltViewModel
class DsgSearchVm @Inject constructor(
    private val dsgSearchRepo: DsgSearchRepo,
    private val geocoder: Geocoder,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private var textChangedJob: Job? = null

    private val errorSate: MutableStateFlow<String> = MutableStateFlow(
        EMPTY_STRING
    )

    fun observeErrorState(): StateFlow<String> {
        return errorSate
    }

    private val dsgListState: MutableStateFlow<String> =
        MutableStateFlow("")

    fun observeDsgList(): StateFlow<String> {
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

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun clearSearch() {
        dsgListState.value = EMPTY_STRING
        textChangedJob?.cancel()
    }

    fun search(searchQuery: String) {
        viewModelScope.launch {
            withContext(dispatcher) {
                dsgSearchRepo.getSearchData(searchQuery)
                    .catch {
                        errorSate.value
                    }
                    .collect {
                        when (it) {
                            is ApiResult.Success<String> -> {
                                // countryListState.emit(it.value)
                                Log.d("Collect", it.toString())
                                dsgListState.value = it.value

                            }
                            else -> {
                                errorSate.value = it.toString()
                            }
                        }

                    }
            }
        }
    }

    fun searchByDebounce(query: String) {
        var searchFor = ""
        if (query.length > 1) {
            val searchText = query.trim()
            if (searchText != searchFor) {
                searchFor = searchText

                textChangedJob?.cancel()
                textChangedJob = viewModelScope.launch(Dispatchers.Main) {
                    delay(700L)
                    if (searchText == searchFor) {
                        search(searchText)
                    }
                }
            }
        }
    }

    fun getCountryByLocation(location: Location) {
        if (Build.VERSION.SDK_INT >= 33) {
            geocoder.getFromLocation(
                location.latitude,
                location.longitude,
                1,
                (Geocoder.GeocodeListener { addresses: MutableList<Address> ->
                    var country = addresses[0].countryName
                    isAmericaStateFlow.value = country.equals(CONSTANT_STRING_USA)
                })
            )
        } else {
            val addressList = geocoder.getFromLocation(
                location.latitude,
                location.longitude,
                1
            )
            if ((addressList != null && addressList.size > 0)) {
                var country = addressList?.get(0)?.countryName
                isAmericaStateFlow.value = country.equals(CONSTANT_STRING_USA)
            }
        }
    }

}