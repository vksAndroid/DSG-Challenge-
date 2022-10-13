package countryinfo.app.repo

import countryinfo.app.api.ApiInterface
import countryinfo.app.api.RetrofitClient
import countryinfo.app.api.model.CountryData
import countryinfo.app.local.CountriesDao
import countryinfo.app.utils.ApiResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CountryListRepo @Inject constructor(var client: ApiInterface, var dao: CountriesDao) {


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
        val response = RetrofitClient.retrofit.getCountryByName(name)
        if (response.isSuccessful) {
            response.body()?.let {
                emit(ApiResult.Success(it))
            }

        } else {
            emit(ApiResult.Failure(response.message(), Throwable(response.errorBody().toString())))
        }
    }

    suspend fun addtoFavourite(countryItem: CountryData) {
        dao.insertFavourite(countryItem)
    }


    suspend fun removeFromFavourite(name: String) {
        dao.deleteFavourite(name)
    }


    suspend fun getALlFavourite(): List<CountryData> {
        return dao.getAllFavorite()
    }

    suspend fun isCountryFav(name: String): CountryData {
        return dao.isFav(name)
    }

}