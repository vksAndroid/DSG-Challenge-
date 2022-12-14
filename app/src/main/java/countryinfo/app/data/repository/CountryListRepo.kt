package countryinfo.app.data.repository

import countryinfo.app.data.ApiInterface
import countryinfo.app.data.model.CountryData
import countryinfo.app.data.local.CountriesDao
import countryinfo.app.utils.ApiResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CountryListRepo @Inject constructor(private var client: ApiInterface, private var dao: CountriesDao) {
    /**
     * Get country list
     *
     * @return list of countries
     */
    fun getCountryList(): Flow<ApiResult<List<CountryData>>> = flow {
        emit(ApiResult.Loading)
        val response = client.getCountryList()
        if (response.isSuccessful) {
            response.body()?.let {
                emit(ApiResult.Success(it))
            }
        } else {
            emit(ApiResult.Failure(response.message(), Throwable(response.errorBody().toString())))
        }
    }


    /**
     * Get countries by name
     *
     * @param name
     * @return list of searched countries
     */
    fun getCountriesByName(name: String): Flow<ApiResult<List<CountryData>>> = flow {
        emit(ApiResult.Loading)
        val response = client.getCountryByName(name)
        if (response.isSuccessful) {
            response.body()?.let {
                emit(ApiResult.Success(it))
            }
        } else {
            emit(ApiResult.Failure(response.message(), Throwable(response.errorBody().toString())))
        }
    }

    /**
     * Add to Favourite in Database
     *
     * @param countryItem
     * @return Long id operation
     */
    suspend fun addToFavourite(countryItem: CountryData): Long {
        return dao.addToFavourite(countryItem)
    }

    /**
     * Remove favourite from database
     *
     * @param name
     * @return Int value for operation
     */
    suspend fun removeFromFavourite(name: String) :Int {
        return dao.removeFromFavourite(name)
    }

    /**
     * get all saved countries from database
     *
     * @return List of country which are saved
     */
    suspend fun getALlFavourite(): List<CountryData> {
        return dao.getAllFavorite()
    }

    /**
     * Check country added or not in favourite database
     *
     * @param name
     * @return null or CountryData fro operation
     */
    suspend fun isCountryFav(name: String): CountryData? {
        return dao.isFav(name)
    }

}