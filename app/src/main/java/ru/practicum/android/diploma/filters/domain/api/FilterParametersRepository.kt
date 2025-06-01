package ru.practicum.android.diploma.filters.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.filters.domain.models.FilterParameters
import ru.practicum.android.diploma.filters.domain.models.FilterParametersType

interface FilterParametersRepository {

    fun getFilterParameters(): Flow<FilterParameters>
    fun getFilterParametersObserver(): Flow<FilterParameters>
    suspend fun deleteFilters()
    suspend fun saveFilterParameters(parameters: FilterParametersType)
    suspend fun restoreFilters(filters: FilterParameters)
}
