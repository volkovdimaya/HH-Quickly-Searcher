package ru.practicum.android.diploma.workterritories.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.common.domain.models.WorkTerritory
import ru.practicum.android.diploma.common.presentation.ListUiState
import ru.practicum.android.diploma.workterritories.domain.impl.WorkTerritoriesInteractor

class WorkTerritoriesViewModel(private val workTerritoriesInteractor: WorkTerritoriesInteractor) : ViewModel() {

    init{
        getCurrentWorkTerritories()
    }

    private var workTerritoryLiveData = MutableLiveData(WorkTerritory())
    fun getWorkTerritoryLiveData(): MutableLiveData<WorkTerritory> = workTerritoryLiveData

    private fun getCurrentWorkTerritories() {
        viewModelScope.launch {
            workTerritoriesInteractor.getWorkTerritories()
                .collect { region ->
                    workTerritoryLiveData.postValue(region)
                }
        }
    }

    fun createfilt() {
        viewModelScope.launch {
            workTerritoriesInteractor.createfilt()
                .collect { region ->
                  //  workTerritoryLiveData.postValue(region)
                }
        }
    }

    fun deleteCountryFilter() {
        viewModelScope.launch {
            workTerritoriesInteractor.deleteCountryFilter()
                .collect { code ->
                    //  workTerritoryLiveData.postValue(region)
                }
        }
    }

    fun deleteRegionFilter() {
        viewModelScope.launch {
            workTerritoriesInteractor.deleteRegionFilter()
                .collect { code ->
                    //  workTerritoryLiveData.postValue(region)
                }
        }
    }

}
