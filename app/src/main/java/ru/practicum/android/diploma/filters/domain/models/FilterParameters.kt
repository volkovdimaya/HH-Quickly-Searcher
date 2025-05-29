package ru.practicum.android.diploma.filters.domain.models

data class FilterParameters(
    val countryId: Int? = null,
    val regionId: Int? = null,
    val industryId: String? = null,
    val salary: Int? = null,
    val onlyWithSalary: Boolean = false,
    val countryName: String? = null,
    val regionName: String? = null,
    val industryName: String? = null,
    val needToSearch: Boolean = false
)
