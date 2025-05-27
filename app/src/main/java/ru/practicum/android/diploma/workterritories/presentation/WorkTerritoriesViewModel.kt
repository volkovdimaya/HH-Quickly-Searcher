package ru.practicum.android.diploma.workterritories.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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

        }
    }
}
