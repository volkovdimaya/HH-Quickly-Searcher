package ru.practicum.android.diploma.search.mapper

import ru.practicum.android.diploma.common.domain.models.Country
import ru.practicum.android.diploma.common.domain.models.RegionWork
import ru.practicum.android.diploma.common.domain.models.WorkTerritory
import ru.practicum.android.diploma.regions.data.dto.AreaDto

object WorkTerritoryMapper {
    fun map(areaDto: AreaDto): WorkTerritory {
        val country = Country(
            countryId = areaDto.parentId?.toIntOrNull() ?: 0,
            countryName = "Россия" // Значение по умолчанию
        )

        val regionWork = RegionWork(
            regionId = areaDto.id.toIntOrNull(),
            regionName = areaDto.name,
            country = country
        )

        return WorkTerritory(regionWork, country)
    }
}
