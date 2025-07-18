package ru.practicum.android.diploma.workterritories.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "areas")
class AreaEntity(
    @PrimaryKey
    val areaId: Int,
    val areaName: String,
    val parentId: String? = null
)
