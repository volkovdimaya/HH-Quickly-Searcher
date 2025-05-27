package ru.practicum.android.diploma.workterritories.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.common.domain.models.WorkTerritory

interface WorkTerritoriesRepository {

    fun getWorkTerritories(): Flow<WorkTerritory>
    fun createfilt(): Flow<Boolean>
    fun deleteRegionFilter(): Flow<Int>
    fun deleteCountryFilter(): Flow<Int>

}
