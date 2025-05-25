package ru.practicum.android.diploma.filters.data.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.filters.domain.api.FilterParametersRepository
import ru.practicum.android.diploma.filters.domain.models.FilterParametersDomain

class FilterParametersRepositoryImpl : FilterParametersRepository {

    override fun getFilterParameters(): Flow<FilterParametersDomain> {
        TODO()
    }

    override suspend fun saveFilterParameters(parameters: FilterParametersDomain) {
        TODO()
    }

    override fun isFiltersEmpty(): Flow<Boolean> {
        TODO()
    }
}
