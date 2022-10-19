package countryinfo.app.data

import countryinfo.app.data.model.CountryData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

interface ApiInterface {


    @GET("/v3.1/all")
    suspend fun getCountryList(): Response<List<CountryData>>

    @GET("/v3.1/name/{query}")
    suspend fun getCountryByName(@Path("query") query: String): Response<List<CountryData>>

    @GET
    suspend fun getDsgSearchByName(@Url url : String): Response<String>

}