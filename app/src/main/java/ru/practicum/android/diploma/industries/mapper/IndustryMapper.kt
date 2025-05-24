package ru.practicum.android.diploma.industries.mapper

import ru.practicum.android.diploma.industries.data.dto.IndustryCategoryDto
import ru.practicum.android.diploma.industries.data.dto.IndustryDto
import ru.practicum.android.diploma.industries.data.entity.IndustryEntity
import ru.practicum.android.diploma.industries.domain.models.Industry

object IndustryMapper {

    fun IndustryEntity.toIndustry(): Industry {
        return Industry(
            industryId = this.industryId,
            industryName = this.industryName
        )
    }

    private fun IndustryEntity.toDto(): IndustryDto {
        return IndustryDto(
            id = this.industryId,
            name = this.industryName
        )
    }

    private fun IndustryDto.toIndustry(): Industry {
        return Industry(
            industryId = this.id,
            industryName = this.name,
        )
    }

    private fun IndustryCategoryDto.toDto(): IndustryDto {
        return IndustryDto(
            id = this.id,
            name = this.name,
        )
    }

    fun IndustryDto.toEntity(): IndustryEntity {
        return IndustryEntity(
            industryId = this.id,
            industryName = this.name,
        )
    }

    private fun Industry.toDto(): IndustryDto {
        return IndustryDto(
            id = this.industryId,
            name = this.industryName,
        )
    }

    private fun Industry.toEntity(): IndustryEntity {
        return IndustryEntity(
            industryId = this.industryId,
            industryName = this.industryName
        )
    }

    fun mapIndustryCategoryDtoToIndustryDto(
        industriesCategoryDto: List<IndustryCategoryDto>
    ): List<IndustryDto> {
        val list = mutableListOf<IndustryDto>()
        industriesCategoryDto.forEach { industryCategory ->
            list.add(industryCategory.toDto())
            list.addAll(industryCategory.industries)

        }
        return list
    }

    fun mapIndustryDtoToIndustry(
        industriesDto: List<IndustryDto>
    ): List<Industry> {
        return industriesDto.map { it.toIndustry() }
    }
}
