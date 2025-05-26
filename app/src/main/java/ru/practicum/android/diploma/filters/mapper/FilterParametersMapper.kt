package ru.practicum.android.diploma.filters.mapper

import ru.practicum.android.diploma.filters.data.entity.FilterParametersEntity
import ru.practicum.android.diploma.filters.domain.models.FilterParameters
import ru.practicum.android.diploma.search.data.dto.FilterParametersDto
import ru.practicum.android.diploma.search.domain.models.FilterParametersSearch

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

    fun FilterParameters.toSearch(): FilterParametersSearch {
        return FilterParametersSearch(
            countryId,
            regionId,
            industryId,
            salary,
            onlyWithSalary
        )
    }

    fun FilterParametersEntity.toDomain(): FilterParameters {
        return FilterParameters(
            this.countryId,
            this.regionId,
            this.industryId,
            this.salary,
            this.onlyWithSalary,
            this.countryName,
            this.regionName,
            this.industryName
        )
    }
}
