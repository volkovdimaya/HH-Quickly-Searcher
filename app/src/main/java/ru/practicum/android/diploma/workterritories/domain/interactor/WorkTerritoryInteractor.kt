package ru.practicum.android.diploma.workterritories.domain.interactor

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.common.domain.models.WorkTerritory

interface WorkTerritoryInteractor {

    fun getWorkTerritories(): Flow<WorkTerritory>

    suspend fun deleteRegionFilter()

    suspend fun deleteCountryFilter()
}
