package ru.practicum.android.diploma.common.data.models

import androidx.room.Embedded
import androidx.room.Relation
import ru.practicum.android.diploma.favorites.data.entity.VacancyEntity
import ru.practicum.android.diploma.workterritories.data.entity.AreaEntity

data class VacancyWithWorkTerritory(
    @Embedded val vacancy: VacancyEntity,

    @Relation(parentColumn = "workTerritoryId", entityColumn = "areaId")
    val region: AreaEntity?,

    @Relation(parentColumn = "workTerritoryCountryId", entityColumn = "areaId")
    val country: AreaEntity
)
