package ru.practicum.android.diploma.workterritories.data.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.practicum.android.diploma.common.data.db.AppDatabase
import ru.practicum.android.diploma.common.domain.models.WorkTerritory
import ru.practicum.android.diploma.workterritories.domain.api.WorkTerritoriesRepository
import ru.practicum.android.diploma.workterritories.mapper.WorkTerritoriesMapper.toWorkTerritory

class WorkTerritoryRepositoryImpl(
    private val appDatabase: AppDatabase
) : WorkTerritoriesRepository {

    override fun getWorkTerritories(): Flow<WorkTerritory> {
        return appDatabase.areaDao().observeFilterParameters()
            .map { it?.toWorkTerritory() ?: WorkTerritory() }
    }

    override suspend fun deleteRegionFilter() {
        appDatabase.areaDao().updateRegion(null, null)
    }

    override suspend fun deleteCountryAndRegionFilter() {
        appDatabase.areaDao().updateCountry(null, null)
        deleteRegionFilter()
    }
}
