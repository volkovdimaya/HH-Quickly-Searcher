package ru.practicum.android.diploma.search.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.common.domain.models.VacancyShort
import ru.practicum.android.diploma.common.presentation.BaseSearchViewModel
import ru.practicum.android.diploma.common.presentation.ListUiState
import ru.practicum.android.diploma.filters.domain.api.FilterParametersInteractor
import ru.practicum.android.diploma.filters.domain.models.FilterParameters
import ru.practicum.android.diploma.filters.domain.models.FilterParametersType
import ru.practicum.android.diploma.search.domain.VacanciesInteractor
import ru.practicum.android.diploma.search.domain.models.FilterParametersSearch

class SearchViewModel(
    private val vacanciesInteractor: VacanciesInteractor,
    private val filterParametersInteractor: FilterParametersInteractor
) : BaseSearchViewModel<VacancyShort>() {

    private var loadMoreJob: Job? = null

    private var currentFilters: FilterParameters? = null
    private var searchedFilters: FilterParameters? = null

    private var currentPage: Int = 0
    private var maxPages: Int = 0
    private var totalFound: Int = 0
    private var isNextPageLoading: Boolean = false
    private var vacanciesList: MutableList<VacancyShort> = mutableListOf()

    private val isFiltersEmptyState = MutableLiveData<Boolean>()
    fun isFiltersEmpty(): LiveData<Boolean> = isFiltersEmptyState

    init {
        getFilters()
    }

    override fun onSearchTextChanged(text: String) {
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

    override fun searchString() {
        resetPaginationState()
        super.searchString()
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
            screenStateLiveData.postValue(ListUiState.Loading)
            searchString()
            searchedFilters = currentFilters
        }
    }

    fun getFilters() {
        viewModelScope.launch {
            vacanciesInteractor.getFilterParameters().collect {
                isFiltersEmptyState.postValue(isFilterEmpty(it.second))
                Log.d("getFilterParameters", " ${it}")
                currentFilters = it.second
                if (it.first) {
                    updateRequest(currentQuery)
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

    override suspend fun runSearch(currentQuery: String) {
        val domainFilters = currentFilters
        vacanciesInteractor.searchVacancies(currentQuery, domainFilters, currentPage)
            .catch {
                screenStateLiveData.postValue(ListUiState.ServerError)
            }
            .collectLatest { searchResult ->
                when {
                    searchResult.resultCode == INTERNAL_ERROR_CODE -> screenStateLiveData.postValue(ListUiState.Error)
                    searchResult.resultCode != SUCCESS_CODE -> screenStateLiveData.postValue(ListUiState.ServerError)
                    else -> {
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

    override fun onClickDebounce(item: VacancyShort) {
        screenStateLiveData.postValue(ListUiState.AnyItem(item.vacancyId))
    }

    private fun isFilterEmpty(filterParameters: FilterParameters): Boolean {
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

    fun saveSearchUiState(pos: Int?) {
        screenStateLiveData.postValue(
            SearchWithPagingUiState
                .ContentWithMetadataRestate(
                    SearchWithPagingUiState.ContentWithMetadata(
                        contentList = vacanciesList.toList(),
                        totalFound = totalFound,
                        pages = maxPages,
                        currentPage = currentPage
                    ),
                    pos
                )
        )
    }

    companion object {
        private const val SUCCESS_CODE = 200
        private const val BAD_REQUEST_CODE = 400
        private const val INTERNAL_ERROR_CODE = 500
    }
}
