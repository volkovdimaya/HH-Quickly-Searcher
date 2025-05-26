package ru.practicum.android.diploma.search.presentation

import androidx.lifecycle.LiveData
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
import ru.practicum.android.diploma.search.domain.models.FilterParametersSearch
import ru.practicum.android.diploma.search.presentation.api.VacanciesInteractor
import ru.practicum.android.diploma.util.debounce

class SearchViewModel(private val vacanciesInteractor: VacanciesInteractor) : ViewModel() {

    private var searchJob: Job? = null
    private var loadMoreJob: Job? = null
    private var searchDebounceJob: Job? = null

    private var screenStateLiveData = MutableLiveData<ShortVacancyListUiState>(ShortVacancyListUiState.Default)
    private var previousScreenStateLiveData = MutableLiveData<ShortVacancyListUiState>()

    private var currentQuery: String = ""
    private var currentFilters: FilterParametersSearch? = null
    private var searchedFilters: FilterParametersSearch? = null
    private var lastSearchedQuery: String = ""

    private var currentPage: Int = 0
    private var maxPages: Int = 0
    private var totalFound: Int = 0
    private var isNextPageLoading: Boolean = false
    private var vacanciesList: MutableList<VacancyShort> = mutableListOf()

    val observeState = MediatorLiveData<ShortVacancyListUiState>().apply {
        addSource(screenStateLiveData) { newValue ->
            previousScreenStateLiveData.value = this.value
            this.value = newValue
        }
    }

    private val isFiltersEmptyState = MutableLiveData<Boolean>()
    fun isFiltersEmpty(): LiveData<Boolean> = isFiltersEmptyState

    private val searchDebouncer: (String) -> Unit
    private val onItemClickDebouncer: (VacancyShort) -> Unit

    init {
        searchDebouncer = debounce<String>(
            delayMillis = SEARCH_DEBOUNCE_DELAY,
            coroutineScope = viewModelScope,
            useLastParam = true
        ) { query ->
            if (query.isNotEmpty() && query == currentQuery && query != lastSearchedQuery) {
                resetPaginationState()
                screenStateLiveData.postValue(ShortVacancyListUiState.Loading)
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

        viewModelScope.launch {
            vacanciesInteractor.getSearchFilters().collect {
                isFiltersEmptyState.postValue(isFilterEmpty(it))
                currentFilters = it
            }
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

    fun updateRequest(query: String) {
        val queryChanged = currentQuery != query
        val filtersChanged = currentFilters != searchedFilters

        currentQuery = query

        searchJob?.cancel()
        searchDebounceJob?.cancel()

        if (query.isBlank()) {
            screenStateLiveData.postValue(ShortVacancyListUiState.Default)
            return
        }

        if (queryChanged || filtersChanged || lastSearchedQuery != query) {
            resetPaginationState()
            screenStateLiveData.postValue(ShortVacancyListUiState.Loading)
            searchVacancies()
            searchedFilters = currentFilters
        }
    }

    private fun resetPaginationState() {
        currentPage = 0
        maxPages = 0
        totalFound = 0
        vacanciesList.clear()
        isNextPageLoading = false
    }

    private fun searchVacancies() {
        searchJob?.cancel()

        if (currentQuery.isBlank()) {
            screenStateLiveData.postValue(ShortVacancyListUiState.Default)
            return
        }

        lastSearchedQuery = currentQuery

        searchJob = viewModelScope.launch {
            val domainFilters = currentFilters

            vacanciesInteractor.searchVacancies(currentQuery, domainFilters, currentPage)
                .catch {
                    screenStateLiveData.postValue(ShortVacancyListUiState.Error)
                }
                .collectLatest { searchResult ->
                    maxPages = searchResult.pages
                    totalFound = searchResult.found

                    val vacancies = searchResult.vacancies
                    vacanciesList.clear()
                    vacanciesList.addAll(vacancies)

                    val screenState = if (vacanciesList.isNotEmpty()) {
                        ShortVacancyListUiState.ContentWithMetadata(
                            contentList = vacanciesList.toList(),
                            totalFound = totalFound,
                            pages = maxPages,
                            currentPage = currentPage
                        )
                    } else {
                        ShortVacancyListUiState.Empty
                    }

                    screenStateLiveData.postValue(screenState)
                }
        }
    }

    private fun loadNextPage() {
        if (isNextPageLoading || currentPage >= maxPages - 1) {
            return
        }

        isNextPageLoading = true
        currentPage++

        screenStateLiveData.postValue(ShortVacancyListUiState.LoadingMore(vacanciesList.toList()))

        loadMoreJob = viewModelScope.launch {
            val domainFilters = currentFilters

            vacanciesInteractor.searchVacancies(currentQuery, domainFilters, currentPage)
                .catch {
                    isNextPageLoading = false
                    screenStateLiveData.postValue(ShortVacancyListUiState.LoadingMoreError(vacanciesList.toList()))
                }
                .collectLatest { searchResult ->
                    isNextPageLoading = false
                    val newVacancies = searchResult.vacancies

                    screenStateLiveData.postValue(
                        ShortVacancyListUiState.NewItems(
                            newItems = newVacancies,
                            totalFound = totalFound
                        )
                    )

                    vacanciesList.addAll(newVacancies)
                }
        }
    }

    fun onLastItemReached() {
        if (!isNextPageLoading && currentPage < maxPages - 1) {
            loadNextPage()
        }
    }

    fun showVacancyDetails(item: VacancyShort) {
        onItemClickDebouncer(item)
    }

    private fun onClickDebounce(item: VacancyShort) {
        screenStateLiveData.postValue(ShortVacancyListUiState.AnyItem(item.vacancyId))
    }

    private fun isFilterEmpty(filterParameters: FilterParametersSearch): Boolean {
        var result = true
        if (filterParameters.salary != null
            || filterParameters.onlyWithSalary) {
            result = false
        }
        if (filterParameters.regionId != null
            || filterParameters.countryId != null
            || filterParameters.industryId != null
        ) {
            result = false
        }
        return result
    }

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 2_000L
        private const val CLICK_DEBOUNCE_DELAY = 1_000L
    }
}
