package ru.practicum.android.diploma.search.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.search.domain.models.FilterParametersSearch
import ru.practicum.android.diploma.search.domain.models.SearchResult
import ru.practicum.android.diploma.vacancy.domain.models.VacancyDetail

interface VacanciesRepository {

    fun searchVacancies(
        text: String,
        filters: FilterParametersSearch?,
        page: Int = 0,
    ): Flow<SearchResult>

    fun getVacancyDetails(id: Int): Flow<VacancyDetail?>
}
