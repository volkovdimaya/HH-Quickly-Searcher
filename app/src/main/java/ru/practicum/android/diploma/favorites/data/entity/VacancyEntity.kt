package ru.practicum.android.diploma.favorites.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "favorites"
)
data class VacancyEntity(
    @PrimaryKey
    val id: String,
    val vacancyName: String,
    val workTerritory: String,
    val salaryFrom: Int?,
    val salaryTo: Int?,
    val salaryCurrencyAbbr: String,
    val logoUrl: String,
    val employer: String,
    val description: String,
    val address: String,
    val keySkills: List<String>?,
    val employment: String,
    val workFormat: String,
    val experience: String,
    val vacancyUrl: String
)
