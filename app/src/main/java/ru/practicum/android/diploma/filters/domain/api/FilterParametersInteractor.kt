package ru.practicum.android.diploma.filters.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.search.domain.models.FilterParametersSearch

interface FilterParametersInteractor {

    fun getSearchFilterParameters(): Flow<FilterParametersSearch>
}
