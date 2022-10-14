package countryinfo.app.vm

import android.annotation.SuppressLint
import android.location.Location
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.OnTokenCanceledListener
import com.google.gson.Gson
import countryinfo.app.api.model.CountryData
import countryinfo.app.di.hiltmodules.DefaultDispatcher
import countryinfo.app.di.hiltmodules.IoDispatcher
import countryinfo.app.repo.CountryListRepo
import countryinfo.app.utils.ApiResult
import countryinfo.app.utils.ScreenOptions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

@HiltViewModel
class CountryListVm @Inject constructor(
    private val countryListRepo: CountryListRepo,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    @Inject
    lateinit var mFusedLocationClient: FusedLocationProviderClient

    var timer: Timer? = null

    var isFav = mutableStateOf(false)

    var title = mutableStateOf("")


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
        timer?.cancel()
    }

    private var _searchQuery = MutableStateFlow("")
    fun searchQuery(): StateFlow<String> {
        return _searchQuery
    }

    private val currentLocationStateFlow: MutableStateFlow<Location> =
        MutableStateFlow(Location(""))

    fun observeCurrentLocation(): StateFlow<Location> {
        return currentLocationStateFlow
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
                countryListRepo.getCountryList()
                    .catch { exception ->
                        ApiResult.Failure(exception.message, exception.cause)
                    }.collect {
                        when (it) {
                            is ApiResult.Success<List<CountryData>> -> {

                                countryListState.emit(it.value)
                            }
                            is ApiResult.Failure -> {
                                ApiResult.Failure(it.message, it.throwable)
                            }
                            else -> {
                                ApiResult.Failure("", Throwable(""))
                            }
                        }
                    }
            }
        }
    }

    /**
     * Schedule search
     *
     * @param name
     */
    fun scheduleSearch(name: String) {
        if (name.length > 1) {
            timer?.cancel()
            timer = Timer()
            timer?.schedule(object : TimerTask() {
                override fun run() {
                    getCountriesByName(name)
                }
            }, 1000)
        }
    }

    /**
     * Get countries by name
     *
     * @param query : It is a search query used to search countries by name
     */
    fun getCountriesByName(query: String) {
        viewModelScope.launch {
            countryListRepo.getCountriesByName(query)
                .catch {

                }
                .collect {
                    when (it) {
                        is ApiResult.Success<List<CountryData>> -> {
                            searchCountryListState.emit(it.value)
                        }
                        is ApiResult.Failure -> {
                            ApiResult.Failure(it.message, it.throwable)
                        }
                        else -> {
                            ApiResult.Failure("", Throwable(""))
                        }
                    }
                }
        }
    }

    @SuppressLint("MissingPermission")
    fun getCurrentLatLong() {
        viewModelScope.launch {
            withContext(dispatcher) {
                mFusedLocationClient?.getCurrentLocation(
                    Priority.PRIORITY_HIGH_ACCURACY,
                    object : CancellationToken() {
                        override fun onCanceledRequested(p0: OnTokenCanceledListener) =
                            CancellationTokenSource().token

                        override fun isCancellationRequested() = false
                    })?.addOnSuccessListener { location ->
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
                countryListRepo.addtoFavourite(countryItem)
                isFav.value = true
            }
        }

    }

    fun removeFavourite(countryItem: CountryData) {
        viewModelScope.launch {
            withContext(dispatcher) {
                countryItem.cca3?.let {
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