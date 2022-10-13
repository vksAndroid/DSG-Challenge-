package countryinfo.app.vm

import android.annotation.SuppressLint
import android.location.Location
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.FusedLocationProviderClient
import countryinfo.app.api.model.CountryData
import countryinfo.app.api.model.CurrenciesName
import countryinfo.app.di.hiltmodules.DefaultDispatcher
import countryinfo.app.repo.CountryListRepo
import countryinfo.app.utils.ApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject
import kotlin.collections.set

@HiltViewModel
class CountryListVm @Inject constructor(
    private val countryListRepo: CountryListRepo,
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    var timer: Timer? = null

    var data = mutableStateOf("")

    private val countryListState: MutableStateFlow<List<CountryData>> =
        MutableStateFlow(emptyList())

    fun observeCountryList(): StateFlow<List<CountryData>> {
        return countryListState
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

    fun getFormattedData(countryItem: CountryData): LinkedHashMap<String, String> {
        var currenciesFormatted = String()
        for (currency in countryItem.currencies) {
            currenciesFormatted = "$currenciesFormatted ${currency.key} (${currency.value.name}), "
        }
        currenciesFormatted = currenciesFormatted.removeSuffix(", ")
        val formattedLanguages = countryItem.languages.values.toString().removeSurrounding("[", "]")

        val formattedCountryDetail = linkedMapOf<String, String>()
        formattedCountryDetail["Capital"] = countryItem.capital[0]
        formattedCountryDetail["Region"] = countryItem?.region!!
        formattedCountryDetail["Subregion"] = countryItem?.subregion!!
        formattedCountryDetail["Languages"] = formattedLanguages
        formattedCountryDetail["Currencies"] = currenciesFormatted
        formattedCountryDetail["Population"] = countryItem.population.toString()
        formattedCountryDetail["Car Driver Side"] = countryItem?.car?.side!!
        return formattedCountryDetail
    }

    fun getFormattedLanguages(languages: Map<String, String>): String {
        return languages.values.toString().removeSurrounding("[", "]")
    }

    fun getFormattedCurrencies(currencies: Map<String, CurrenciesName>): String {
        var currenciesFormatted = String()
        for (currency in currencies) {
            currenciesFormatted = "$currenciesFormatted ${currency.key} (${currency.value.name}), "
        }
        return currenciesFormatted.removeSuffix(", ")
    }

    @SuppressLint("MissingPermission")
    fun getCurrentLatLong(mFusedLocationClient: FusedLocationProviderClient) {
        mFusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                if (location != null)
                    currentLocationStateFlow.value = location
            }
    }
}