package ru.practicum.android.diploma.filters.data.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import ru.practicum.android.diploma.common.data.db.AppDatabase
import ru.practicum.android.diploma.filters.data.entity.FilterParametersEntity
import ru.practicum.android.diploma.filters.domain.api.FilterParametersRepository
import ru.practicum.android.diploma.filters.domain.api.FilterParametersType
import ru.practicum.android.diploma.filters.domain.models.FilterDelete
import ru.practicum.android.diploma.filters.domain.models.FilterParameters
import ru.practicum.android.diploma.filters.mapper.FilterParametersMapper.toDomain

class FilterParametersRepositoryImpl(
    private val database: AppDatabase
) : FilterParametersRepository {
    override fun getFilterParameters(): Flow<FilterParameters>   = flow{
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
        val currentFilters = database.filterParametersDao().getFilters(FILTER_DB_ID)
        if (currentFilters.isNotEmpty()) {
            database.filterParametersDao().deleteFilters(currentFilters[0])
        }
    }



    override suspend fun saveFilterParameters(parameters: FilterParametersType) {
        val currentFilters = database.filterParametersDao().getFilters(FILTER_DB_ID)
        var newFilters = if (currentFilters.isEmpty()) {
            FilterParametersEntity()
        } else {
            currentFilters[0]
        }
        newFilters = updateFiltersWithType(newFilters, parameters)
        database.filterParametersDao().createFilters(newFilters)
    }

    private fun updateFiltersWithType(
        newFilters: FilterParametersEntity,
        parameters: FilterParametersType
    ): FilterParametersEntity {
        return when (parameters) {
            is FilterParametersType.Country -> {
                newFilters.copy(
                    countryId = parameters.countryId,
                    countryName = parameters.countryName
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
}
