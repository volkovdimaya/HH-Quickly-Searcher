package ru.practicum.android.diploma.workterritories.presentation.models

import ru.practicum.android.diploma.common.domain.models.WorkTerritory
import ru.practicum.android.diploma.filters.domain.api.FilterParametersType

sealed class WorkTerritoriesState {
    object default : WorkTerritoriesState()
    class SelectedArea(val country : Country, val region : Area) : WorkTerritoriesState()
}
