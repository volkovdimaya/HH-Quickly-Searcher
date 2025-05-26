package ru.practicum.android.diploma.search.domain.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.practicum.android.diploma.filters.domain.api.FilterParametersRepository
import ru.practicum.android.diploma.filters.mapper.FilterParametersMapper.toSearch
import ru.practicum.android.diploma.search.domain.models.FilterParametersSearch
import ru.practicum.android.diploma.search.domain.api.VacanciesRepository
import ru.practicum.android.diploma.search.domain.models.SearchResult
import ru.practicum.android.diploma.search.presentation.api.VacanciesInteractor
import ru.practicum.android.diploma.vacancy.domain.models.VacancyDetail

class VacanciesInteractorImpl(
    private val repository: VacanciesRepository,
    private val filtersRepository: FilterParametersRepository
) : VacanciesInteractor {

    override fun searchVacancies(
        query: String,
        filters: FilterParametersSearch?,
        page: Int,
    ): Flow<SearchResult> {
        return repository.searchVacancies(query, filters, page)
    }

    override fun getVacancyDetails(id: Int): Flow<VacancyDetail?> {
        return repository.getVacancyDetails(id)
    }

    override fun getSearchFilters(): Flow<FilterParametersSearch> {
        return filtersRepository.getFilterParameters().map {
            it.toSearch()
        }
    }

}
