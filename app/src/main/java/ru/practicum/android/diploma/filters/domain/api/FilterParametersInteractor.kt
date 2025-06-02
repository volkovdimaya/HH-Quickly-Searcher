package ru.practicum.android.diploma.filters.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.filters.domain.models.FilterParameters
import ru.practicum.android.diploma.filters.domain.models.FilterParametersType
import ru.practicum.android.diploma.search.domain.models.FilterParametersSearch

interface FilterParametersInteractor {

    fun getSearchFilterParameters(): Flow<FilterParametersSearch>
    fun getFilterParameters(): Flow<FilterParameters>
    suspend fun deleteAllFilters()
    suspend fun updateFilterParameter(parameter: FilterParametersType)
    fun notifyUpdateSearchRequest()
}
