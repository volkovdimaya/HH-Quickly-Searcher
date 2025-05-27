package ru.practicum.android.diploma.workterritories.data.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.common.domain.models.RegionWork
import ru.practicum.android.diploma.workterritories.domain.api.WorkTerritoriesRepository

class WorkTerritoriesRepositoryImpl : WorkTerritoriesRepository {

    override fun getWorkTerritories(): Flow<RegionWork> {
        while (true) {
            //
        }
    }
}
