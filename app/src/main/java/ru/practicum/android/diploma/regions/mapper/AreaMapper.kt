package ru.practicum.android.diploma.regions.mapper

import ru.practicum.android.diploma.regions.data.dto.AreaDto
import ru.practicum.android.diploma.regions.domain.models.Region
import ru.practicum.android.diploma.workterritories.data.entity.AreaEntity

object AreaMapper {

    fun AreaEntity.toRegion(): Region {
        return Region(
            regionId = this.areaId.toString(),
            regionName = this.areaName
        )
    }

    private fun AreaDto.toRegion(): Region {
        return Region(
            regionId = this.id,
            regionName = this.name
        )
    }

    fun AreaDto.toEntity(): AreaEntity {
        return AreaEntity(
            areaId = this.id.toInt(),
            areaName = this.name,
            parentId = this.parentId,
        )
    }

    fun flattenAreaDtoList(areaDtoList: List<AreaDto>): List<AreaDto> {
        val flattenedList = mutableListOf<AreaDto>()

        fun addAreasRecursively(areas: List<AreaDto>) {
            areas.forEach { area ->
                flattenedList.add(area)
                if (!area.areas.isNullOrEmpty()) {
                    addAreasRecursively(area.areas)
                }
            }
        }

        addAreasRecursively(areaDtoList)
        return flattenedList
    }

    fun mapAreaDtoToRegion(areasDto: List<AreaDto>): List<Region> {
        return areasDto.map { it.toRegion() }
    }
}
