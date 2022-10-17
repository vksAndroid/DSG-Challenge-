package countryinfo.app.vm

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import countryinfo.app.di.hiltmodules.IoDispatcher
import countryinfo.app.repo.DsgSearchRepo
import countryinfo.app.utils.ApiResult
import countryinfo.app.utils.EMPTY_STRING
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import javax.inject.Inject

@HiltViewModel
class DsgSearchVm @Inject constructor(
    private val dsgSearchRepo: DsgSearchRepo,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private var textChangedJob: Job? = null

    private val errorSate: MutableStateFlow<String> = MutableStateFlow(
        EMPTY_STRING
    )

    fun observeErrorState(): StateFlow<String> {
        return errorSate
    }

    private val dsgListState: MutableStateFlow<String> =
        MutableStateFlow("")

    fun observeDsgList(): StateFlow<String> {
        return dsgListState
    }

    private var _searchQuery = MutableStateFlow(EMPTY_STRING)
    fun searchQuery(): StateFlow<String> {
        return _searchQuery
    }

    fun clearSearch() {
        dsgListState.value = EMPTY_STRING
        textChangedJob?.cancel()
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun search(searchQuery: String) {
        viewModelScope.launch {
            withContext(dispatcher) {
                dsgSearchRepo.getSearchData(searchQuery)
                    .catch {
                        errorSate.value
                    }
                    .collect {
                        when (it) {
                            is ApiResult.Success<String> -> {
                                // countryListState.emit(it.value)
                                Log.d("Collect", it.toString())
                                dsgListState.value = it.value

                            }
                            else -> {
                                errorSate.value = it.toString()
                            }
                        }

                    }
            }
        }
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
                        search(searchText)
                    }
                }
            }
        }
    }
}