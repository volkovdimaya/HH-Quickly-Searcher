package ru.practicum.android.diploma.common.presentation

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.util.debounce

abstract class BaseSearchViewModel<T> : ViewModel() {

    var searchJob: Job? = null
    var searchDebounceJob: Job? = null

    var screenStateLiveData = MutableLiveData<ListUiState<T>>(ListUiState.Default)
    private var previousScreenStateLiveData = MutableLiveData<ListUiState<T>>()

    var currentQuery: String = ""
    var lastSearchedQuery: String = ""

    private var list: MutableList<T> = mutableListOf()

    val observeState = MediatorLiveData<ListUiState<T>>().apply {
        addSource(screenStateLiveData) { newValue ->
            previousScreenStateLiveData.value = this.value
            this.value = newValue
        }
    }

    val searchDebouncer: (String) -> Unit
    val onItemClickDebouncer: (T) -> Unit

    init {
        searchDebouncer = debounce<String>(
            delayMillis = SEARCH_DEBOUNCE_DELAY,
            coroutineScope = viewModelScope,
            useLastParam = true
        ) { query ->
            if (query.isNotEmpty() && query == currentQuery && query != lastSearchedQuery) {
                screenStateLiveData.postValue(ListUiState.Loading)
                searchString()
            }
        }

        onItemClickDebouncer = debounce<T>(
            delayMillis = CLICK_DEBOUNCE_DELAY,
            coroutineScope = viewModelScope,
            useLastParam = false
        ) { item ->
            onClickDebounce(item)
        }
    }

    abstract suspend fun runSearch(currentQuery: String)

    open fun searchString() {
        searchJob?.cancel()

        if (currentQuery.isBlank()) {
            screenStateLiveData.postValue(ListUiState.Default)
            return
        }

        lastSearchedQuery = currentQuery
        screenStateLiveData.postValue(ListUiState.Loading)
        searchJob = viewModelScope.launch {
            runSearch(currentQuery)
        }
    }

    fun restoreState() {
        screenStateLiveData.postValue(previousScreenStateLiveData.value)
    }

    open fun onSearchTextChanged(text: String) {
        val textChanged = currentQuery != text
        currentQuery = text

        if (currentQuery.isEmpty()) {
            searchDebounceJob?.cancel()
            return
        } else if (textChanged) {
            searchDebounceJob?.cancel()
            searchDebounceJob = viewModelScope.launch {
                searchDebouncer(text)
            }
        }
    }

    abstract fun onClickDebounce(item: T)

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 2_000L
        private const val CLICK_DEBOUNCE_DELAY = 1_000L
    }
}
