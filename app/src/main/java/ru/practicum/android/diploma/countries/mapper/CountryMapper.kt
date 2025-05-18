package ru.practicum.android.diploma.countries.mapper

import ru.practicum.android.diploma.common.domain.models.Country
import ru.practicum.android.diploma.workterritories.data.entity.AreaEntity

object CountryMapper {

    fun AreaEntity.toCountry(): Country {
        return Country(
            countryId = this.areaId.toInt(),
            countryName = this.areaName,
        )
    }
}
