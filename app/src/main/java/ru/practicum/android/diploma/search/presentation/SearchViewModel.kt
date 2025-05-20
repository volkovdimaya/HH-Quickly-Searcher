package ru.practicum.android.diploma.search.presentation

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.common.domain.models.VacancyShort
import ru.practicum.android.diploma.common.presentation.ShortVacancyListUiState
import ru.practicum.android.diploma.common.ui.models.FilterParameters
import ru.practicum.android.diploma.search.presentation.api.VacanciesInteractor
import ru.practicum.android.diploma.util.debounce

class SearchViewModel(private val vacanciesInteractor: VacanciesInteractor) : ViewModel() {

    private var searchJob: Job? = null
    private var searchDebounceJob: Job? = null

    private var screenStateLiveData = MutableLiveData<ShortVacancyListUiState>(ShortVacancyListUiState.Default)
    private var previousScreenStateLiveData = MutableLiveData<ShortVacancyListUiState>()

    private var currentQuery: String = ""
    private var currentFilters: FilterParameters? = null
    private var lastSearchedQuery: String = ""

    val observeState = MediatorLiveData<ShortVacancyListUiState>().apply {
        addSource(screenStateLiveData) { newValue ->
            previousScreenStateLiveData.value = this.value
            this.value = newValue
        }
    }

    private val searchDebouncer: (String) -> Unit
    private val onItemClickDebouncer: (VacancyShort) -> Unit

    init {
        searchDebouncer = debounce<String>(
            delayMillis = SEARCH_DEBOUNCE_DELAY,
            coroutineScope = viewModelScope,
            useLastParam = true
        ) { query ->
            if (query.isNotEmpty() && query == currentQuery && query != lastSearchedQuery) {
                searchVacancies()
            }
        }

        onItemClickDebouncer = debounce<VacancyShort>(
            delayMillis = CLICK_DEBOUNCE_DELAY,
            coroutineScope = viewModelScope,
            useLastParam = false
        ) { item ->
            onClickDebounce(item)
        }
    }

    fun restoreState() {
        screenStateLiveData.postValue(previousScreenStateLiveData.value)
    }

    fun onSearchTextChanged(text: String) {
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

    fun updateRequest(query: String, filters: FilterParameters? = null) {
        val queryChanged = currentQuery != query
        val filtersChanged = currentFilters != filters

        currentQuery = query
        currentFilters = filters

        searchJob?.cancel()
        searchDebounceJob?.cancel()

        if (query.isBlank()) {
            screenStateLiveData.postValue(ShortVacancyListUiState.Default)
            return
        }

        if (queryChanged || filtersChanged || lastSearchedQuery != query) {
            screenStateLiveData.postValue(ShortVacancyListUiState.Loading)
            searchVacancies()
        }
    }

    private fun searchVacancies() {
        searchJob?.cancel()

        if (currentQuery.isBlank()) {
            screenStateLiveData.postValue(ShortVacancyListUiState.Default)
            return
        }

        lastSearchedQuery = currentQuery

        screenStateLiveData.postValue(ShortVacancyListUiState.Loading)

        searchJob = viewModelScope.launch {
            vacanciesInteractor.searchVacancies(currentQuery, currentFilters)
                .catch {
                    screenStateLiveData.postValue(ShortVacancyListUiState.Error)
                }
                .collectLatest { vacancies ->
                    val screenState = if (vacancies.isNotEmpty()) {
                        ShortVacancyListUiState.Content(vacancies)
                    } else {
                        ShortVacancyListUiState.Empty
                    }

                    screenStateLiveData.postValue(screenState)
                }
        }
    }

    fun showVacancyDetails(item: VacancyShort) {
        onItemClickDebouncer(item)
    }

    private fun onClickDebounce(item: VacancyShort) {
        screenStateLiveData.postValue(ShortVacancyListUiState.AnyItem(item.vacancyId))
    }

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 2_000L
        private const val CLICK_DEBOUNCE_DELAY = 1_000L
    }
}
