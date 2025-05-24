package ru.practicum.android.diploma.industries.domain.models

data class Industry(
    val industryId: String,
    val industryName: String,
    var select: Boolean = false
)
