package ru.practicum.android.diploma.workterritories.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.common.domain.models.RegionWork

interface WorkTerritoriesRepository {

    fun getWorkTerritories(): Flow<RegionWork>

}
