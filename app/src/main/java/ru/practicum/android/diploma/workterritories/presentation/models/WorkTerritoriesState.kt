package ru.practicum.android.diploma.workterritories.presentation.models

import ru.practicum.android.diploma.common.domain.models.WorkTerritory

sealed class WorkTerritoriesState {
    object default : WorkTerritoriesState()
    class SelectedCountry(val area: WorkTerritory) : WorkTerritoriesState()
}
