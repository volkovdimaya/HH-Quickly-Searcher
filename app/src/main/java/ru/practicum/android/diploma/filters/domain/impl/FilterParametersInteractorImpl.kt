package ru.practicum.android.diploma.filters.domain.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.practicum.android.diploma.filters.domain.api.FilterParametersInteractor
import ru.practicum.android.diploma.filters.domain.api.FilterParametersRepository
import ru.practicum.android.diploma.filters.mapper.FilterParametersMapper.toSearch
import ru.practicum.android.diploma.search.domain.models.FilterParametersSearch

class FilterParametersInteractorImpl(
    private val filterParametersRepository: FilterParametersRepository
) : FilterParametersInteractor {

    override fun getSearchFilterParameters(): Flow<FilterParametersSearch> {
        return filterParametersRepository.getFilterParameters().map {
            it.toSearch()
        }
    }
}
