package ru.practicum.android.diploma.search.data.dto

data class FilterParametersDto(
    val countryId: Int? = null,
    val regionId: Int? = null,
    val industryId: String? = null,
    val salary: Int? = null,
    val onlyWithSalary: Boolean = false
)
