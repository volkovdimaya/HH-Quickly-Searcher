package ru.practicum.android.diploma.workterritories.mapper

import ru.practicum.android.diploma.common.domain.models.Country
import ru.practicum.android.diploma.common.domain.models.WorkTerritory
import ru.practicum.android.diploma.filters.data.entity.FilterParametersEntity
import ru.practicum.android.diploma.regions.domain.models.Region

object WorkTerritoriesMapper {
    fun FilterParametersEntity.toWorkTerritory(): WorkTerritory {
        return WorkTerritory(
            country = if (countryId.toString().isBlank()) {
                null
            } else {
                this.countryId?.let { Country(it, this.countryName ?: "") }
            },
            regionWork = if (regionId.toString().isNullOrBlank()) {
                null
            } else {
                Region(
                    this.regionId.toString(),
                    this.regionName ?: "",
                    (this.countryId ?: "").toString(),
                )
            },
        )
    }
}
