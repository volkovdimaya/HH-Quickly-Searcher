package ru.practicum.android.diploma.filters.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.filters.domain.models.FilterParametersDomain

interface FilterParametersRepository {

    fun getFilterParameters(): Flow<FilterParametersDomain>
    suspend fun saveFilterParameters(parameters: FilterParametersDomain)
    fun isFiltersEmpty(): Flow<Boolean>
}
