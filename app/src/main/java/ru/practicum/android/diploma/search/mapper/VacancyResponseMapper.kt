package ru.practicum.android.diploma.search.mapper

import ru.practicum.android.diploma.common.domain.models.VacancyShort
import ru.practicum.android.diploma.search.data.dto.VacancyDto

object ShortVacancyResponseMapper {
    fun map(vacancyDto: VacancyDto): VacancyShort {
        return VacancyShort(
            id = vacancyDto.id,
            name = vacancyDto.name,
            employer = vacancyDto.employer?.name ?: "",
            city = vacancyDto.area?.name ?: "",
            salary = SalaryResponseMapper.map(vacancyDto.salary),
            logoUrl = vacancyDto.employer?.logoUrls?.firstOrNull() ?: ""
        )
    }
}
