package ru.practicum.android.diploma.filters.domain.models

data class FilterParametersDomain(
    val countryId: Int? = null,
    val regionId: Int? = null,
    val industryId: Int? = null,
    val salary: Int? = null,
    val onlyWithSalary: Boolean = false,
)
