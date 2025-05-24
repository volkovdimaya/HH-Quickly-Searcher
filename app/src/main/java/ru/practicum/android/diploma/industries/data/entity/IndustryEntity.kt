package ru.practicum.android.diploma.industries.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "industries"
)
data class IndustryEntity(
    @PrimaryKey
    val industryId: String,
    val industryName: String
)
