package ru.practicum.android.diploma.search.domain.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import ru.practicum.android.diploma.filters.domain.api.FilterParametersRepository
import ru.practicum.android.diploma.filters.domain.models.FilterParameters
import ru.practicum.android.diploma.search.domain.VacanciesInteractor
import ru.practicum.android.diploma.search.domain.api.VacanciesRepository
import ru.practicum.android.diploma.search.domain.models.SearchResult
import ru.practicum.android.diploma.vacancy.domain.models.VacancyDetail

class VacanciesInteractorImpl(
    private val vacanciesRepository: VacanciesRepository,
    private val filtersRepository: FilterParametersRepository,
) : VacanciesInteractor {

    override val filterEvents: SharedFlow<Unit> = filtersRepository.refreshNotifier

    override fun searchVacancies(
        query: String,
        filters: FilterParameters?,
        page: Int,
    ): Flow<SearchResult> {
        return vacanciesRepository.searchVacancies(query, filters, page)
    }

    override fun getVacancyDetails(id: Int): Flow<VacancyDetail?> {
        return vacanciesRepository.getVacancyDetails(id)
    }

    override fun getFilterParametersObserver(): Flow<FilterParameters> {
        return filtersRepository.getFilterParametersObserver()
    }

}
