package ru.practicum.android.diploma.favorites.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import ru.practicum.android.diploma.workterritories.data.entity.AreaEntity

@Entity(
    tableName = "favorites",
    foreignKeys = [
        ForeignKey(
            entity = AreaEntity::class,
            parentColumns = ["areaId"],
            childColumns = ["workTerritoryId"],
            onDelete = ForeignKey.NO_ACTION
        )
    ],
)
data class VacancyEntity(
    @PrimaryKey
    val id: String,
    val vacancyName: String,
    val workTerritoryId: String,
    val industry: String,
    val salaryFrom: Int?,
    val salaryTo: Int?,
    val salaryCurrencyAbbr: String,
    val logoUrl: String,
    val employer: String,
    val description: String,
    val address: String,
    val employment: String,
    val experience: String
)
