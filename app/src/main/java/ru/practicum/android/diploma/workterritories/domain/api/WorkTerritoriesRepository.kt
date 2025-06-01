package ru.practicum.android.diploma.workterritories.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.common.domain.models.WorkTerritory

interface WorkTerritoriesRepository {

    fun getWorkTerritories(): Flow<WorkTerritory>
    suspend fun deleteRegionFilter()
    suspend fun deleteCountryAndRegionFilter()

}
