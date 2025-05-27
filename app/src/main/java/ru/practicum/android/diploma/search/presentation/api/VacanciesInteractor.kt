package ru.practicum.android.diploma.search.presentation.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.search.domain.models.FilterParametersSearch
import ru.practicum.android.diploma.search.domain.models.SearchResult
import ru.practicum.android.diploma.vacancy.domain.models.VacancyDetail

interface VacanciesInteractor {

    fun searchVacancies(
        query: String,
        filters: FilterParametersSearch?,
        page: Int = 0,
    ): Flow<SearchResult>

    fun getVacancyDetails(id: Int): Flow<VacancyDetail?>

    fun getSearchFilters(): Flow<FilterParametersSearch>
}
