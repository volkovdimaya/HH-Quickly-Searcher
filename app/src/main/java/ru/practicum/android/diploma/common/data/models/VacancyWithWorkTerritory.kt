package ru.practicum.android.diploma.common.data.models

import androidx.room.Embedded
import ru.practicum.android.diploma.favorites.data.entity.VacancyEntity

data class VacancyWithWorkTerritory(
    @Embedded val vacancy: VacancyEntity,
//
//    @Relation(
//        parentColumn = "workTerritoryRegionId",
//        entityColumn = "areaId"
//    )
//    val region: AreaEntity?,
//
//    @Relation(
//        parentColumn = "workTerritoryCountryId",
//        entityColumn = "areaId"
//    )
//
//    val country: AreaEntity?
)
