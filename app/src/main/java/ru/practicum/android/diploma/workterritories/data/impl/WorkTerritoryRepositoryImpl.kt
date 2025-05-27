package ru.practicum.android.diploma.workterritories.data.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.practicum.android.diploma.common.data.db.AppDatabase
import ru.practicum.android.diploma.common.domain.models.WorkTerritory
import ru.practicum.android.diploma.filters.data.entity.FilterParametersEntity
import ru.practicum.android.diploma.workterritories.domain.api.WorkTerritoriesRepository
import ru.practicum.android.diploma.workterritories.mapper.WorkTerritoriesMapper.toWorkTerritory

class WorkTerritoryRepositoryImpl(
    private val appDatabase: AppDatabase
) : WorkTerritoriesRepository {

    override fun getWorkTerritories(): Flow<WorkTerritory> {
        return appDatabase.filterUpdateParametersDao().observeFilterParameters()
            .map { it?.toWorkTerritory() ?: WorkTerritory() }

    }

    override suspend fun deleteRegionFilter() {
        val filterParametersEntity =
            FilterParametersEntity()
        appDatabase.areaDao().updateAreaParameter(filterParametersEntity)

    }

    override suspend fun deleteCountryFilter() {
        appDatabase.areaDao().updateCountry(null, null)
        deleteRegionFilter()
    }
}
