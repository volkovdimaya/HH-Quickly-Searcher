package ru.practicum.android.diploma.workterritories.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.common.domain.models.WorkTerritory
import ru.practicum.android.diploma.workterritories.domain.interactor.WorkTerritoryInteractor

class WorkTerritoriesViewModel(private val workTerritoryInteractor: WorkTerritoryInteractor) : ViewModel() {

    init {
        getCurrentWorkTerritories()
    }

    private var workTerritoryLiveData = MutableLiveData(WorkTerritory())
    fun getWorkTerritoryLiveData(): MutableLiveData<WorkTerritory> = workTerritoryLiveData

    private fun getCurrentWorkTerritories() {
        viewModelScope.launch {
            workTerritoryInteractor.getWorkTerritories()
                .collect { region ->
                    if (region != null) {
                        workTerritoryLiveData.postValue(region)
                    } else {
                        workTerritoryLiveData.postValue(WorkTerritory())
                    }
                }
        }
    }

    fun deleteCountryFilter() {
        viewModelScope.launch {
            workTerritoryInteractor.deleteCountryFilter()
        }
    }

    fun deleteRegionFilter() {
        viewModelScope.launch {
            workTerritoryInteractor.deleteRegionFilter()
        }
    }
}
