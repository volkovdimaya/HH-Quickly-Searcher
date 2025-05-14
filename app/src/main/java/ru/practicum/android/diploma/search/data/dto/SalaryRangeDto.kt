package ru.practicum.android.diploma.search.data.dto

data class SalaryRangeDto(
    val currency: String,
    val from: Int? = null,
    val to: Int? = null,
)
