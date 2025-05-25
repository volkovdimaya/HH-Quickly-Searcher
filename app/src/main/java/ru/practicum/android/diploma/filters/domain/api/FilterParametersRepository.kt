package ru.practicum.android.diploma.filters.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.common.domain.api.FilterParametersInterface
import ru.practicum.android.diploma.common.domain.api.FilterParametersType
import ru.practicum.android.diploma.filters.domain.models.FilterParametersDetails

interface FilterParametersRepository {

    fun getFilterParameters(): Flow<FilterParametersInterface>
    suspend fun saveFilterParameters(parameters: FilterParametersType)
    fun isFiltersEmpty(): Flow<Boolean>
}
