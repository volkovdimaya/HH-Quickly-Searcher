package ru.practicum.android.diploma.filters.data.dto

data class FilterParametersDto(
    val countryId: Int? = null,
    val regionId: Int? = null,
    val industryId: Int? = null,
    val salary: Int? = null,
    val onlyWithSalary: Boolean = false,

)
