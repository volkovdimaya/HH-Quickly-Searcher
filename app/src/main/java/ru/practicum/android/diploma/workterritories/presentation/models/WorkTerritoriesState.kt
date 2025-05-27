package ru.practicum.android.diploma.workterritories.presentation.models

import ru.practicum.android.diploma.common.domain.models.Country
import ru.practicum.android.diploma.regions.domain.models.Region

sealed class WorkTerritoriesState {
    data class SelectedArea(val country: Country? = null, val region: Region? = null) : WorkTerritoriesState()
}
