package ru.practicum.android.diploma.filters.data.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.common.domain.api.FilterParametersInterface
import ru.practicum.android.diploma.common.domain.api.FilterParametersType
import ru.practicum.android.diploma.filters.domain.api.FilterParametersRepository
import ru.practicum.android.diploma.filters.domain.models.FilterParametersDetails

class FilterParametersRepositoryImpl : FilterParametersRepository {
    override fun getFilterParameters(): Flow<FilterParametersInterface> {
        TODO("Not yet implemented")
    }

    override suspend fun saveFilterParameters(parameters: FilterParametersType) {
        TODO("Not yet implemented")
    }

    override fun isFiltersEmpty(isFilter: Any): Flow<Boolean> {
        TODO("Not yet implemented")
    }


}
