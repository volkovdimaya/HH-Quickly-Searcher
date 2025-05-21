package ru.practicum.android.diploma.filters.domain.models

import ru.practicum.android.diploma.filters.data.dto.FilterParametersDto

data class FilterParametersDomain(
    val countryId: Int? = null,
    val regionId: Int? = null,
    val industryId: Int? = null,
    val salary: Int? = null,
    val onlyWithSalary: Boolean = false,
)

fun FilterParametersDomain.toDto(): FilterParametersDto {
    return FilterParametersDto(
        countryId = this.countryId,
        regionId = this.regionId,
        industryId = this.industryId,
        salary = this.salary,
        onlyWithSalary = this.onlyWithSalary
    )
}
