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
import ru.practicum.android.diploma.common.presentation.ListUiState
import ru.practicum.android.diploma.filters.domain.api.FilterParametersInteractor
import ru.practicum.android.diploma.filters.domain.models.FilterParametersType
import ru.practicum.android.diploma.search.domain.models.FilterParametersSearch
import ru.practicum.android.diploma.search.presentation.api.VacanciesInteractor
import ru.practicum.android.diploma.util.debounce

class SearchViewModel(
    private val vacanciesInteractor: VacanciesInteractor,
    private val filterParametersInteractor: FilterParametersInteractor
) : ViewModel() {

    private var searchJob: Job? = null
    private var loadMoreJob: Job? = null
    private var searchDebounceJob: Job? = null

    private var screenStateLiveData = MutableLiveData<ListUiState<VacancyShort>>(ListUiState.Default)
    private var previousScreenStateLiveData = MutableLiveData<ListUiState<VacancyShort>>()

    private var currentQuery: String = ""
    private var currentFilters: FilterParametersSearch? = null
    private var searchedFilters: FilterParametersSearch? = null
    private var lastSearchedQuery: String = ""

    private var currentPage: Int = 0
    private var maxPages: Int = 0
    private var totalFound: Int = 0
    private var isNextPageLoading: Boolean = false
    private var vacanciesList: MutableList<VacancyShort> = mutableListOf()

    val observeState = MediatorLiveData<ListUiState<VacancyShort>>().apply {
        addSource(screenStateLiveData) { newValue ->
            previousScreenStateLiveData.value = this.value
            this.value = newValue
        }
    }

    fun getPreviousScreenState(): LiveData<ListUiState<VacancyShort>> = previousScreenStateLiveData

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
                screenStateLiveData.postValue(ListUiState.Loading)
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

        getFilters()
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
            screenStateLiveData.postValue(ListUiState.Default)
            return
        }

        if (queryChanged || filtersChanged || lastSearchedQuery != query) {
            resetPaginationState()
            screenStateLiveData.postValue(ListUiState.Loading)
            searchVacancies()
            searchedFilters = currentFilters
        }
    }

    fun getFilters() {
        viewModelScope.launch {
            filterParametersInteractor.getSearchFilterParameters().collect {
                isFiltersEmptyState.postValue(isFilterEmpty(it))
                currentFilters = it
                if (it.needToSearch) {
                    updateRequest(currentQuery)
                    filterParametersInteractor.updateFilterParameter(FilterParametersType.NeedToSearch())
                }
            }
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
            screenStateLiveData.postValue(ListUiState.Default)
            return
        }

        lastSearchedQuery = currentQuery

        searchJob = viewModelScope.launch {
            val domainFilters = currentFilters
            vacanciesInteractor.searchVacancies(currentQuery, domainFilters, currentPage)
                .catch {
                    screenStateLiveData.postValue(ListUiState.ServerError)
                }
                .collectLatest { searchResult ->
                    maxPages = searchResult.pages
                    totalFound = searchResult.found

                    val vacancies = searchResult.vacancies
                    vacanciesList.clear()
                    vacanciesList.addAll(vacancies)

                    val screenState = if (vacanciesList.isNotEmpty()) {
                        SearchWithPagingUiState.ContentWithMetadata(
                            contentList = vacanciesList.toList(),
                            totalFound = totalFound,
                            pages = maxPages,
                            currentPage = currentPage
                        )
                    } else {
                        ListUiState.Empty
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

        screenStateLiveData.postValue(SearchWithPagingUiState.LoadingMore(vacanciesList.toList()))

        loadMoreJob = viewModelScope.launch {
            val domainFilters = currentFilters

            vacanciesInteractor.searchVacancies(currentQuery, domainFilters, currentPage)
                .catch {
                    isNextPageLoading = false
                    screenStateLiveData.postValue(SearchWithPagingUiState.LoadingMoreError(vacanciesList.toList()))
                }
                .collectLatest { searchResult ->
                    isNextPageLoading = false
                    val newVacancies = searchResult.vacancies

                    screenStateLiveData.postValue(
                        SearchWithPagingUiState.NewItems(
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
        screenStateLiveData.postValue(ListUiState.AnyItem(item.vacancyId))
    }

    private fun isFilterEmpty(filterParameters: FilterParametersSearch): Boolean {
        var result = true
        listOf(
            filterParameters.salary,
            filterParameters.regionId,
            filterParameters.countryId,
            filterParameters.industryId
        ).forEach { parameter ->
            if (parameter != null) {
                result = false
            }
        }
        if (filterParameters.onlyWithSalary) {
            result = false
        }
        return result
    }

    fun updateShortVacancyListNewItems(pos: Int?) {
        screenStateLiveData.postValue(
            SearchWithPagingUiState
                .ContentWithMetadataRestate(
                    SearchWithPagingUiState.ContentWithMetadata(
                        contentList = vacanciesList.toList(),
                        totalFound = totalFound,
                        pages = maxPages,
                        currentPage = currentPage
                    ), pos
                )
        )

    }

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 2_000L
        private const val CLICK_DEBOUNCE_DELAY = 1_000L
    }
}
