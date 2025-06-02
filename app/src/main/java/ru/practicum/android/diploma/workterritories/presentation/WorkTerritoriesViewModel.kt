package ru.practicum.android.diploma.workterritories.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.common.domain.models.WorkTerritory
import ru.practicum.android.diploma.workterritories.domain.interactor.WorkTerritoryInteractor

class WorkTerritoriesViewModel(private val workTerritoryInteractor: WorkTerritoryInteractor) : ViewModel() {

    private var workTerritoryLiveData = MutableLiveData<WorkTerritory>()
    fun getWorkTerritoryLiveData(): LiveData<WorkTerritory> = workTerritoryLiveData

    init {
        getCurrentWorkTerritories()
    }

    private fun getCurrentWorkTerritories() {
        viewModelScope.launch {
            workTerritoryInteractor.getWorkTerritories()
                .collect { region ->
                    workTerritoryLiveData.postValue(region)
                }
        }
    }

    fun deleteCountryAndRegionFilter() {
        viewModelScope.launch {
            workTerritoryInteractor.deleteCountryAndRegionFilter()
        }
    }

    fun deleteRegionFilter() {
        viewModelScope.launch {
            workTerritoryInteractor.deleteRegionFilter()
        }
    }
}
