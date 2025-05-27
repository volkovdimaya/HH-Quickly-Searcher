package ru.practicum.android.diploma.workterritories.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.regions.domain.models.Region
import ru.practicum.android.diploma.workterritories.presentation.api.InteractorWorkTerritories
import ru.practicum.android.diploma.workterritories.presentation.models.WorkTerritoriesState

class WorkTerritoriesViewModel(
    val interactorWorkTerritories: InteractorWorkTerritories
) : ViewModel() {
    private var _state = MutableLiveData<WorkTerritoriesState>()
    val state: LiveData<WorkTerritoriesState>
        get() = _state!!

    init {
        viewModelScope.launch(Dispatchers.IO) {
            combine(
                interactorWorkTerritories.getCountry(),
                interactorWorkTerritories.getRegion()
            ) { country, region ->
                WorkTerritoriesState.SelectedArea(country, region)
            }.collect { selectedArea ->
                _state.postValue(selectedArea)
            }
        }
    }

    fun deleteCountry() {
        viewModelScope.launch(Dispatchers.IO) {
            interactorWorkTerritories.deleteCountry()
        }
    }

    fun deleteRegion() {
        viewModelScope.launch(Dispatchers.IO) {
            interactorWorkTerritories.deleteRegion()
        }
    }

}
