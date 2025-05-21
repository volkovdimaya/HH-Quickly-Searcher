package ru.practicum.android.diploma.favorites.data.mapper

import ru.practicum.android.diploma.common.data.models.VacancyWithWorkTerritory
import ru.practicum.android.diploma.common.domain.models.Currency
import ru.practicum.android.diploma.common.domain.models.Salary
import ru.practicum.android.diploma.common.domain.models.VacancyShort
import ru.practicum.android.diploma.common.domain.models.WorkTerritory
import ru.practicum.android.diploma.countries.mapper.CountryMapper.toCountry
import ru.practicum.android.diploma.favorites.data.entity.VacancyEntity
import ru.practicum.android.diploma.industries.domain.models.Industry
import ru.practicum.android.diploma.regions.mapper.RegionMapper.toRegion
import ru.practicum.android.diploma.vacancy.domain.models.VacancyDetail

object ShortVacancyMapper {

    private fun VacancyWithWorkTerritory.toVacancyShort(): VacancyShort {
        return VacancyShort(
            vacancyId = this.vacancy.id,
            vacancyName = this.vacancy.vacancyName,
            salary = Salary(
                salaryFrom = this.vacancy.salaryFrom,
                salaryTo = this.vacancy.salaryTo,
                salaryCurrency = Currency.currencyFromAbbr(this.vacancy.salaryCurrencyAbbr)!!
            ),
            workTerritory = WorkTerritory(region?.toRegion(), country.toCountry()),
            logoUrl = this.vacancy.logoUrl,
            employer = this.vacancy.employer,
        )
    }

    fun mapVacancyEntityToVacancyShort(
        vacanciesEntity: List<VacancyWithWorkTerritory>
    ): List<VacancyShort> {
        return vacanciesEntity.map { it.toVacancyShort() }
    }

    fun VacancyDetail.toVacancyEntity(): VacancyEntity {
        return VacancyEntity(
            id = this.vacancyId.toString(),
            vacancyName = this.vacancyName,
            workTerritoryId = this.workTerritory.regionWork?.regionId.toString(),
            workTerritoryCountryId = this.workTerritory.country.countryId.toString(),
            industry = this.industry.industryName,
            salaryFrom = this.salary.salaryFrom,
            salaryTo = this.salary.salaryTo,
            salaryCurrencyAbbr = this.salary.salaryCurrency.abbr,
            logoUrl = this.logoUrl,
            employer = this.employer,
            description = this.description.toString(),
            address = this.address,
            keySkills = this.keySkills,
            employment = this.employment.toString(),
            experience = this.experience,
        )

    }
}
