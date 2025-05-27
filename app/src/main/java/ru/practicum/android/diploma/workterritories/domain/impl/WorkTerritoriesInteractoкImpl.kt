package ru.practicum.android.diploma.workterritories.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.common.domain.models.WorkTerritory
import ru.practicum.android.diploma.workterritories.domain.api.WorkTerritoriesRepository
import ru.practicum.android.diploma.workterritories.domain.interactor.WorkTerritoriesInteractor

class WorkTerritoriesInteractorImpl(private val workTerritoriesRepository: WorkTerritoriesRepository) :
    WorkTerritoriesInteractor {

    override fun getWorkTerritories(): Flow<WorkTerritory> {
        return workTerritoriesRepository.getWorkTerritories()
    }

    override suspend fun deleteRegionFilter() {
        workTerritoriesRepository.deleteRegionFilter()
    }

    override suspend fun deleteCountryFilter() {
        workTerritoriesRepository.deleteCountryFilter()
    }
}
