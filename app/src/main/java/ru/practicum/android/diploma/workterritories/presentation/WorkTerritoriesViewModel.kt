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

    private var currentStateArea = WorkTerritoriesState.SelectedArea()


    init {
        viewModelScope.launch(Dispatchers.IO) {
            combine(
                interactorWorkTerritories.getCountry(),
                interactorWorkTerritories.getRegion()
            ) { country, region ->
                WorkTerritoriesState.SelectedArea(country, region)
            }.collect { selectedArea ->
                //пришел null и что то еще мы слушаем постоянно
                currentStateArea = selectedArea
                _state.postValue(currentStateArea)
            }
        }
    }

//    private fun updateStateCountry(newState: WorkTerritoriesState.SelectedArea): WorkTerritoriesState.SelectedArea {
//        //нам нужно обязательно удаление
//        if (newState.country == null) {
//            return currentStateArea.copy(country = null)
//        }
//        if(newState.region == null){
//            return currentStateArea.copy(region = null)
//        }
//        //а может быть просто пришел null и не нужно обновлять
//        return currentStateArea.copy(country = newState)
//    }

    private fun updateStateRegion(newState: Region): WorkTerritoriesState.SelectedArea {
        return currentStateArea.copy(region = newState)
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
