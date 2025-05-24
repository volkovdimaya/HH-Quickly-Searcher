package ru.practicum.android.diploma.search.mapper

import ru.practicum.android.diploma.common.domain.models.Currency
import ru.practicum.android.diploma.common.domain.models.Salary
import ru.practicum.android.diploma.common.domain.models.VacancyShort
import ru.practicum.android.diploma.search.data.dto.VacancyDto

object ShortVacancyResponseMapper {
    fun map(vacancyDto: VacancyDto): VacancyShort {
        return VacancyShort(
            vacancyId = vacancyDto.id.toString(),
            vacancyName = vacancyDto.name,
            employer = vacancyDto.employer?.name ?: "",
            workTerritory = vacancyDto.area.name,
            salary = SalaryResponseMapper.map(vacancyDto.salary) ?: Salary(
                null,
                null,
                Currency.RUSSIAN_RUBLE
            ), // тут заглушка
            logoUrl = vacancyDto.employer?.logoUrls?.original ?: ""
        )
    }
}
