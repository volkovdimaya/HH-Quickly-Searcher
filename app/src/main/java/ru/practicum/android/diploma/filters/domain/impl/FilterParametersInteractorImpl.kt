package ru.practicum.android.diploma.filters.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.filters.domain.api.FilterParametersInteractor
import ru.practicum.android.diploma.filters.domain.api.FilterParametersRepository
import ru.practicum.android.diploma.filters.domain.models.FilterParameters
import ru.practicum.android.diploma.filters.domain.models.FilterParametersType

class FilterParametersInteractorImpl(
    private val filterParametersRepository: FilterParametersRepository
) : FilterParametersInteractor {

    override fun getFilterParameters(): Flow<FilterParameters> {
        return filterParametersRepository.getFilterParametersObserver()
    }

    override suspend fun deleteAllFilters() {
        filterParametersRepository.deleteFilters()
    }

    override suspend fun updateFilterParameter(parameter: FilterParametersType) {
        filterParametersRepository.saveFilterParameters(parameter)
    }
}
