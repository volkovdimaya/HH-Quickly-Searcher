package ru.practicum.android.diploma.regions.data.dto

data class AreaDto(
    val id: String,
    val parentId: String?,
    val name: String,
    val areas: List<AreaDto>
)
