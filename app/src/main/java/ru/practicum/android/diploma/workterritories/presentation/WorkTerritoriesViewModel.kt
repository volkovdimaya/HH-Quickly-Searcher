package ru.practicum.android.diploma.workterritories.presentation

import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.workterritories.domain.impl.WorkTerritoriesUseCase

class WorkTerritoriesViewModel(private val workTerritoriesUseCase: WorkTerritoriesUseCase) : ViewModel() {

    init{
        getCurrientWorkTerritories()
    }

    private fun getCurrientWorkTerritories() {
        workTerritoriesUseCase.getWorkTerritories().collect { region ->

        }
    }

}
