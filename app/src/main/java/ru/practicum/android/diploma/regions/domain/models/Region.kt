package ru.practicum.android.diploma.regions.domain.models

data class Region(
    val regionId: String,
    val regionName: String,
    val parentId: String? = null,
)
