package ru.practicum.android.diploma.workterritories.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.common.domain.models.Country
import ru.practicum.android.diploma.common.domain.models.RegionWork
import ru.practicum.android.diploma.workterritories.domain.api.WorkTerritoriesRepository

class WorkTerritoriesUseCase(private val workTerritoriesRepository: WorkTerritoriesRepository) {

    fun getWorkTerritories(): Flow<RegionWork> {
        return workTerritoriesRepository.getWorkTerritories()
    }

}
