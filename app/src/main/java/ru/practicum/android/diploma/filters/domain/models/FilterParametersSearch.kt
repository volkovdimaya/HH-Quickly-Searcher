package ru.practicum.android.diploma.filters.domain.models

import ru.practicum.android.diploma.common.domain.api.FilterParametersInterface

data class FilterParametersSearch(
    val countryId: Int? = null,
    val regionId: Int? = null,
    val industryId: Int? = null,
    val salary: Int? = null,
    val onlyWithSalary: Boolean = false,
) : FilterParametersInterface
