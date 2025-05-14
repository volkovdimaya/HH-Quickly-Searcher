package ru.practicum.android.diploma.favorites.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class VacancyEntity(
    @PrimaryKey
    val id: String,
)
