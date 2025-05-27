package ru.practicum.android.diploma.search.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.filters.domain.models.FilterParameters
import ru.practicum.android.diploma.search.domain.api.VacanciesRepository
import ru.practicum.android.diploma.search.domain.models.SearchResult
import ru.practicum.android.diploma.search.presentation.api.VacanciesInteractor
import ru.practicum.android.diploma.vacancy.domain.models.VacancyDetail

class VacanciesInteractorImpl(private val repository: VacanciesRepository) : VacanciesInteractor {

    override fun searchVacancies(
        query: String,
        filters: FilterParameters?,
        page: Int,
    ): Flow<SearchResult> {
        return repository.searchVacancies(query, filters, page)
    }

    override fun getVacancyDetails(id: Int): Flow<VacancyDetail?> {
        return repository.getVacancyDetails(id)
    }
}
