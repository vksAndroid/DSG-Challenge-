package countryinfo.app.vm

import android.annotation.SuppressLint
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Build
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.OnTokenCanceledListener
import countryinfo.app.api.model.CountryData
import countryinfo.app.di.hiltmodules.IoDispatcher
import countryinfo.app.repo.CountryListRepo
import countryinfo.app.utils.*
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
    private val geocoder: Geocoder,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private var apiJob: Job? = null

    var isFav = mutableStateOf(false)

    var title = mutableStateOf(titleSearch)

    var selectedTab = mutableStateOf(titleSearch)

    private var textChangedJob: Job? = null

    private val UPDATE_INTERVAL = (30 * 1000).toLong()

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

    private var _searchQuery = MutableStateFlow(EMPTY_STRING)
    fun searchQuery(): StateFlow<String> {
        return _searchQuery
    }

    private val currentLocationStateFlow: MutableStateFlow<Location> =
        MutableStateFlow(Location(EMPTY_STRING))

    fun observeCurrentLocation(): StateFlow<Location> {
        return currentLocationStateFlow
    }

    private val isAmericaStateFlow: MutableStateFlow<Boolean> = MutableStateFlow(false)
    fun observeIsAmerica(): StateFlow<Boolean> {
        return isAmericaStateFlow
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