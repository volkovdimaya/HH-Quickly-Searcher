package ru.practicum.android.diploma.favorites.data.mapper

import ru.practicum.android.diploma.common.domain.models.Currency
import ru.practicum.android.diploma.common.domain.models.Salary
import ru.practicum.android.diploma.common.domain.models.VacancyShort
import ru.practicum.android.diploma.favorites.data.entity.VacancyEntity
import ru.practicum.android.diploma.vacancy.domain.models.VacancyDetail

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

    fun VacancyDetail.toVacancyEntity(): VacancyEntity {
        return VacancyEntity(
            id = this.vacancyId,
            vacancyName = this.vacancyName,
            workTerritory = this.workTerritory,
            salaryFrom = this.salary.salaryFrom,
            salaryTo = this.salary.salaryTo,
            salaryCurrencyAbbr = this.salary.salaryCurrency.abbr,
            logoUrl = this.logoUrl,
            employer = this.employer,
            description = this.description,
            address = this.address,
            keySkills = this.keySkills,
            employment = this.employment.employment,
            experience = this.experience,
            workFormat = this.employment.workFormat,
            vacancyUrl = this.vacancyUrl,
        )
    }
}
