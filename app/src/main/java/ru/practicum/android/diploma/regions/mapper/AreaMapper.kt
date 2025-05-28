package ru.practicum.android.diploma.regions.mapper

import ru.practicum.android.diploma.regions.data.dto.AreaDto
import ru.practicum.android.diploma.regions.domain.models.Region
import ru.practicum.android.diploma.workterritories.data.entity.AreaEntity

object AreaMapper {

    fun AreaEntity.toRegion(): Region {
        return Region(
            regionId = this.areaId.toString(),
            regionName = this.areaName,
            parentId = this.parentId
        )
    }

    private fun AreaDto.toRegion(): Region {
        return Region(
            regionId = this.id,
            regionName = this.name,
            parentId = this.parentId

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

        fun addAreasRecursively(areas: List<AreaDto>, rootParentId: String?) {
            areas.forEach { area ->
                val updatedArea = if (rootParentId != null && area.parentId != null) {
                    area.copy(parentId = rootParentId)
                } else {
                    area
                }
                flattenedList.add(updatedArea)

                if (!area.areas.isNullOrEmpty()) {
                    val newRootId = rootParentId ?: area.id
                    addAreasRecursively(area.areas, newRootId)
                }
            }
        }

        addAreasRecursively(areaDtoList, null)
        return flattenedList
    }

    fun mapAreaDtoToRegion(areasDto: List<AreaDto>): List<Region> {
        return areasDto.map { it.toRegion() }
    }
}
