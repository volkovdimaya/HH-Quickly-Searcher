package ru.practicum.android.diploma.favorites.data.mapper

import ru.practicum.android.diploma.common.domain.models.Currency
import ru.practicum.android.diploma.common.domain.models.Salary
import ru.practicum.android.diploma.common.domain.models.VacancyShort
import ru.practicum.android.diploma.favorites.data.entity.VacancyEntity

object ShortVacancyMapper {

    private fun VacancyEntity.toVacancyShort(): VacancyShort {
        return VacancyShort(
            vacancyId = this.id,
            vacancyName = this.vacancyName,
            salary = Salary(
                salaryFrom = this.salaryFrom,
                salaryTo = this.salaryTo,
                salaryCurrency = Currency.currencyFromAbbr(this.salaryCurrencyAbbr)
            ),
            workTerritory = this.workTerritory,
            logoUrl = this.logoUrl,
            employer = this.employer
        )
    }

    fun mapVacancyEntityToVacancyShort(
        vacanciesEntity: List<VacancyEntity>
    ): List<VacancyShort> {
        return vacanciesEntity.map { it.toVacancyShort() }
    }
}
