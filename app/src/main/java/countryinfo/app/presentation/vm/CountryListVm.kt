package countryinfo.app.presentation.vm

import android.annotation.SuppressLint
import android.location.Location
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.OnTokenCanceledListener
import countryinfo.app.data.model.CountryData
import countryinfo.app.di.IoDispatcher
import countryinfo.app.data.repository.CountryListRepo
import countryinfo.app.utils.ApiResult
import countryinfo.app.utils.EMPTY_STRING
import countryinfo.app.utils.ScreenOptions
import countryinfo.app.utils.titleSearch
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import javax.inject.Inject

@HiltViewModel
class CountryListVm @Inject constructor(
    private val countryListRepo: CountryListRepo,
    private val mFusedLocationClient: FusedLocationProviderClient,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private var apiJob: Job? = null

    var isFav = mutableStateOf(false)

    var title = mutableStateOf(titleSearch)

    var selectedTab = mutableStateOf(titleSearch)

    private var textChangedJob: Job? = null

    private val errorSate: MutableStateFlow<String> = MutableStateFlow(
        EMPTY_STRING
    )

    fun observeErrorState(): StateFlow<String> {
        return errorSate
    }


    private val countryListState: MutableStateFlow<List<CountryData>> =
        MutableStateFlow(emptyList())

    fun observeCountryList(): StateFlow<List<CountryData>> {
        return countryListState
    }

    private val countryData: MutableStateFlow<CountryData> =
        MutableStateFlow(CountryData())

    fun observeCountryData(): StateFlow<CountryData> {
        return countryData
    }

    fun updateCountryData(_countryData: CountryData) {
        countryData.value = _countryData
    }

    private val searchCountryListState: MutableStateFlow<List<CountryData>> = MutableStateFlow(
        emptyList()
    )

    fun observeSearchCountryList(): StateFlow<List<CountryData>> {
        return searchCountryListState
    }

    private val savedCountryListState: MutableStateFlow<List<CountryData>> = MutableStateFlow(
        emptyList()
    )

    fun observeSavedCountryList(): StateFlow<List<CountryData>> {
        return savedCountryListState
    }

    private val saveScreenOptions: MutableStateFlow<ScreenOptions> =
        MutableStateFlow(ScreenOptions.SearchScreen)

    fun getSavedScreen(): StateFlow<ScreenOptions> {
        return saveScreenOptions
    }

    fun setSavedScreen(data: ScreenOptions) {
        saveScreenOptions.value = data
    }

    fun clearSearch() {
        searchCountryListState.value = emptyList()
        textChangedJob?.cancel()
    }
    // Clear Error state after receiving error in Launched Effect
     private fun clearErrorState() {
        errorSate.value = EMPTY_STRING
     }

    private var _searchQuery = MutableStateFlow(EMPTY_STRING)
    fun searchQuery(): StateFlow<String> {
        return _searchQuery
    }

    private val currentLocationStateFlow: MutableStateFlow<Location> =
        MutableStateFlow(Location(EMPTY_STRING))

    fun observeCurrentLocation(): StateFlow<Location> {
        return currentLocationStateFlow
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
                        clearErrorState()
                        getCountriesByName(searchText)
                    }
                }
            }
        }
    }


    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    /**
     * Get country list
     * Used to fetch all the countries
     */
    fun getCountryList() {
        viewModelScope.launch {
            withContext(dispatcher) {
                val result = countryListRepo.getCountryList()
                result.catch { exception ->
                    exception.message?.let { errorSate.value }
                    // ApiResult.Failure(exception.message, exception.cause)
                }
                result.collect {
                    when (it) {
                        is ApiResult.Success<List<CountryData>> -> {
                            countryListState.emit(it.value)
                        }
                        is ApiResult.Failure-> {
                            it.message?.let {error->
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

    /**
     * Get countries by name
     *
     * @param query : It is a search query used to search countries by name
     */
    fun getCountriesByName(query: String) {
        searchCountryListState.value = emptyList()

        apiJob?.cancel()
        apiJob = viewModelScope.launch {
            countryListRepo.getCountriesByName(query)
                .catch {
                    it.message?.let { errorSate.value }
                }
                .collect {
                    when (it) {
                        is ApiResult.Success<List<CountryData>> -> {
                            searchCountryListState.emit(it.value)
                        }
                        is ApiResult.Failure-> {
                            searchCountryListState.emit(emptyList())
                            it.message?.let {error->
                                errorSate.value = error
                            }
                         }
                        else -> {}
                    }
                }
        }
    }

    @SuppressLint("MissingPermission")
    fun getCurrentLatLong() {
        viewModelScope.launch {
            withContext(dispatcher) {
                mFusedLocationClient.getCurrentLocation(
                    Priority.PRIORITY_HIGH_ACCURACY,
                    object : CancellationToken() {
                        override fun onCanceledRequested(p0: OnTokenCanceledListener) =
                            CancellationTokenSource().token

                        override fun isCancellationRequested() = false
                    }).addOnSuccessListener { location ->
                    if (location != null) {
                        currentLocationStateFlow.value = location
                    }
                }
            }
        }
    }


    fun addFavourite(countryItem: CountryData) {
        viewModelScope.launch {
            withContext(dispatcher) {
                countryListRepo.addToFavourite(countryItem)
                isFav.value = true
            }
        }

    }

    fun removeFavourite(countryItem: CountryData) {
        viewModelScope.launch {
            withContext(dispatcher) {
                countryItem.cca3.let {
                    countryListRepo.removeFromFavourite(it)
                    isFav.value = false
                }
            }
        }

    }

    fun getALlFavourite() {
        viewModelScope.launch {
            withContext(dispatcher) {
                savedCountryListState.value = countryListRepo.getALlFavourite()
            }
        }
    }

    fun isCountryFav(name: String) {
        viewModelScope.launch {
            withContext(dispatcher) {
                val data = countryListRepo.isCountryFav(name)
                isFav.value = data != null
            }
        }
    }
}