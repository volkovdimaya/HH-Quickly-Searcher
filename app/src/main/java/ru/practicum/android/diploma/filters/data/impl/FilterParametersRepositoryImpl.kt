package ru.practicum.android.diploma.filters.data.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import ru.practicum.android.diploma.common.data.db.AppDatabase
import ru.practicum.android.diploma.filters.data.entity.FilterParametersEntity
import ru.practicum.android.diploma.filters.domain.api.FilterParametersRepository
import ru.practicum.android.diploma.filters.domain.models.FilterParameters
import ru.practicum.android.diploma.filters.domain.models.FilterParametersType
import ru.practicum.android.diploma.filters.mapper.FilterParametersMapper.toDomain

class FilterParametersRepositoryImpl(
    private val database: AppDatabase
) : FilterParametersRepository {

    override fun getFilterParameters(): Flow<FilterParameters> = flow {
        val filters = database.filterParametersDao().getFilters(FILTER_DB_ID)
        if (filters.isEmpty()) {
            emit(FilterParameters())
        } else {
            emit(filters[0].toDomain())
        }
    }

    override fun getFilterParametersObserver(): Flow<FilterParameters> {
        return database.filterParametersDao().getFiltersObserver(FILTER_DB_ID).map {
            it?.toDomain() ?: FilterParameters()
        }
    }

    override suspend fun deleteFilters() {
        database.filterParametersDao().saveFilters(FilterParametersEntity())
    }

    override suspend fun saveFilterParameters(parameters: FilterParametersType) {
        val currentFilters = database.filterParametersDao().getFilters(FILTER_DB_ID)
        var newFilters = if (currentFilters.isEmpty()) {
            FilterParametersEntity(FILTER_DB_ID)
        } else {
            currentFilters[0]
        }
        newFilters = updateFiltersWithType(newFilters, parameters)
        database.filterParametersDao().saveFilters(newFilters)
    }

    private fun updateFiltersWithType(
        newFilters: FilterParametersEntity,
        parameters: FilterParametersType
    ): FilterParametersEntity {
        return when (parameters) {
            is FilterParametersType.Country -> {
                val countryChanged = newFilters.countryId != parameters.countryId
                newFilters.copy(
                    countryId = parameters.countryId,
                    countryName = parameters.countryName,
                    regionId = if (countryChanged) null else newFilters.regionId,
                    regionName = if (countryChanged) null else newFilters.regionName
                )
            }

            is FilterParametersType.Industry -> {
                newFilters.copy(
                    industryName = parameters.industryName,
                    industryId = parameters.industryId
                )
            }

            is FilterParametersType.OnlyWithSalary -> {
                newFilters.copy(onlyWithSalary = parameters.onlyWithSalary)
            }

            is FilterParametersType.Region -> {
                newFilters.copy(
                    regionName = parameters.regionName,
                    regionId = parameters.regionId
                )
            }

            is FilterParametersType.Salary -> {
                newFilters.copy(salary = parameters.salary)
            }
        }
    }

    companion object {
        private const val FILTER_DB_ID = "filter_parameters_id"
    }

    private val _updateSearchNotifier = MutableSharedFlow<Unit>(
        replay = 0,
        extraBufferCapacity = 1
    )
    override val updateSearchNotifier: SharedFlow<Unit> = _updateSearchNotifier

    override fun notifyUpdateSearchRequest() {
        _updateSearchNotifier.tryEmit(Unit)
    }
}
