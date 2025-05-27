package ru.practicum.android.diploma.regions.mapper

import ru.practicum.android.diploma.common.domain.models.RegionWork
import ru.practicum.android.diploma.countries.mapper.CountryMapper.toCountry
import ru.practicum.android.diploma.workterritories.data.entity.AreaEntity

object RegionMapper {

    fun AreaEntity.toRegion(): RegionWork {
        return RegionWork(
            regionId = this.areaId.toInt(),
            regionName = this.areaName,
            country = this.toCountry()
        )
    }

    fun RegionWork.toAreaEntity(): AreaEntity {
        return AreaEntity(
            areaId = this.regionId!!,
            areaName = this.regionName,
            countryId = this.country.countryId.toString()
        )
    }
}
