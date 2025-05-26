package ru.practicum.android.diploma.filters.mapper

import ru.practicum.android.diploma.filters.data.dto.FilterParametersDto
import ru.practicum.android.diploma.filters.domain.models.FilterParametersSearch

object FilterParametersMapper {

    fun FilterParametersSearch.toDto(): FilterParametersDto {
        return FilterParametersDto(
            countryId = this.countryId,
            regionId = this.regionId,
            industryId = this.industryId,
            salary = this.salary,
            onlyWithSalary = this.onlyWithSalary
        )
    }
}
