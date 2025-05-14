package ru.practicum.android.diploma.industries.data.dto

data class IndustryCategoryDto(
    val id: String,
    val name: String,
    val industries: List<IndustryDto>,
)
