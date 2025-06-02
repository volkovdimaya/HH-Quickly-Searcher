package ru.practicum.android.diploma.search.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import ru.practicum.android.diploma.filters.domain.models.FilterParameters
import ru.practicum.android.diploma.search.domain.models.SearchResult
import ru.practicum.android.diploma.vacancy.domain.models.VacancyDetail

interface VacanciesInteractor {

    val filterEvents: SharedFlow<Unit>

    fun searchVacancies(
        query: String,
        filters: FilterParameters?,
        page: Int = 0,
    ): Flow<SearchResult>

    fun getVacancyDetails(id: Int): Flow<VacancyDetail?>

    fun getFilterParametersObserver(): Flow<FilterParameters>
}
