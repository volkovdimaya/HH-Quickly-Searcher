package ru.practicum.android.diploma.search.domain.models

data class FilterParametersSearch(
    val countryId: Int? = null,
    val regionId: Int? = null,
    val industryId: String? = null,
    val salary: Int? = null,
    val onlyWithSalary: Boolean = false,
    val needToSearch: Boolean = false
)
