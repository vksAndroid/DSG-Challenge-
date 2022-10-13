package countryinfo.app.utils

sealed class ApiResult<out R> {
    data class Success<out T>(val value: T): ApiResult<T>()
    data class Failure(
        val message: String?,
        val throwable: Throwable?
    ): ApiResult<Nothing>()
    object Empty : ApiResult<Nothing>()
    object Loading : ApiResult<Nothing>()




}
