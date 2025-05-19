package ru.practicum.android.diploma.favorites.data.mapper

import ru.practicum.android.diploma.common.domain.models.Currency
import ru.practicum.android.diploma.common.domain.models.Salary
import ru.practicum.android.diploma.common.domain.models.VacancyShort
import ru.practicum.android.diploma.common.domain.models.WorkTerritory
import ru.practicum.android.diploma.favorites.data.entity.VacancyEntity
import ru.practicum.android.diploma.industries.domain.models.Industry

object ShortVacancyMapper {

    private suspend fun VacancyEntity.toVacancyShort(
        createWorkTerritory: suspend (areaId: String) -> WorkTerritory
    ): VacancyShort {
        return VacancyShort(
            vacancyId = this.id.toInt(),
            vacancyName = this.vacancyName,
            salary = Salary(
                salaryFrom = this.salaryFrom,
                salaryTo = this.salaryTo,
                salaryCurrency = Currency.currencyFromAbbr(this.salaryCurrencyAbbr)!!
            ),
            workTerritory = createWorkTerritory(this.workTerritoryId),
            logoUrl = this.logoUrl,
            industry = Industry(industryName = this.industry),
            employer = this.employer,
        )
    }

    suspend fun mapVacancyEntityToVacancyShort(
        vacanciesEntity: List<VacancyEntity>,
        createWorkTerritory: suspend (areaId: String) -> WorkTerritory
    ): List<VacancyShort> {
        return vacanciesEntity.map { it.toVacancyShort(createWorkTerritory) }
    }
}
