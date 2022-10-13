package countryinfo.app.utils

import androidx.compose.runtime.MutableState


fun <T : Any> MutableState<ApiResult<T>?>.pagingLoadingState(isLoaded: (pagingState: Boolean) -> Unit) {
    when (this.value) {
        is ApiResult.Success<T> -> {
            isLoaded(false)
        }
        is ApiResult.Loading -> {
            isLoaded(true)
        }
        is ApiResult.Failure -> {
            isLoaded(false)
        }
        else -> {

        }
    }
}