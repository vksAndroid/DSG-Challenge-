package countryinfo.app.repo

import com.google.gson.Gson
import countryinfo.app.api.ApiInterface
import countryinfo.app.api.model.request.SearchVO
import countryinfo.app.utils.ApiResult
import countryinfo.app.utils.dsgUrl
import kotlinx.coroutines.flow.flow
import java.net.URLEncoder.encode
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DsgSearchRepo @Inject constructor(private var client: ApiInterface) {


    suspend fun getSearchData(searchQuery: String) = flow {

        val query: String = encode(
            Gson().toJson(
                SearchVO(
                    searchQuery
                )
            ), Charsets.UTF_8.name()
        )

        val response = client.getDsgSearchByName(
            "${dsgUrl}${query}"
        )

        if (response.isSuccessful) {
            response.body()?.let {
                emit(ApiResult.Success(it))
            }
        } else {
            emit(ApiResult.Failure(response.message(), Throwable(response.errorBody().toString())))
        }
    }

}