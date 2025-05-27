package ru.practicum.android.diploma.workterritories.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.common.domain.models.WorkTerritory
import ru.practicum.android.diploma.workterritories.domain.api.WorkTerritoriesRepository

class WorkTerritoriesInteractor(private val workTerritoriesRepository: WorkTerritoriesRepository) {

    fun getWorkTerritories(): Flow<WorkTerritory> {
        return workTerritoriesRepository.getWorkTerritories()
    }

    fun createfilt(): Flow<Boolean> {
        return workTerritoriesRepository.createfilt()
    }

    fun deleteRegionFilter(): Flow<Int> {
        return workTerritoriesRepository.deleteRegionFilter()
    }

    fun deleteCountryFilter(): Flow<Int> {
        return workTerritoriesRepository.deleteCountryFilter()
    }
}
