package ru.practicum.android.diploma.search.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.common.domain.models.VacancyShort
import ru.practicum.android.diploma.common.ui.models.FilterParameters
import ru.practicum.android.diploma.search.domain.api.SearchRepository
import ru.practicum.android.diploma.search.presentation.api.SearchInteractor

class SearchInteractorImpl(private val repository: SearchRepository) : SearchInteractor {

    override fun searchVacancies(query: String, filters: FilterParameters?): Flow<List<VacancyShort>> {
        return repository.searchVacancies(query, filters)
    }
}

