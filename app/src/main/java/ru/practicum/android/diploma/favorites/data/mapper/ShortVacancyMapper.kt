package ru.practicum.android.diploma.favorites.data.mapper

import ru.practicum.android.diploma.common.data.models.VacancyWithWorkTerritory
import ru.practicum.android.diploma.common.domain.models.Country
import ru.practicum.android.diploma.common.domain.models.Currency
import ru.practicum.android.diploma.common.domain.models.RegionWork
import ru.practicum.android.diploma.common.domain.models.Salary
import ru.practicum.android.diploma.common.domain.models.VacancyShort
import ru.practicum.android.diploma.common.domain.models.WorkTerritory
import ru.practicum.android.diploma.countries.mapper.CountryMapper.toCountry
import ru.practicum.android.diploma.favorites.data.entity.VacancyEntity
import ru.practicum.android.diploma.industries.domain.models.Industry
import ru.practicum.android.diploma.regions.mapper.RegionMapper.toRegion
import ru.practicum.android.diploma.vacancy.domain.models.VacancyDetail

object ShortVacancyMapper {

    private fun VacancyEntity.toVacancyShort(): VacancyShort {
        return VacancyShort(
            vacancyId = this.id.toInt(),
            vacancyName = this.vacancyName,
            salary = Salary(
                salaryFrom = this.salaryFrom,
                salaryTo = this.salaryTo,
                salaryCurrency = Currency.currencyFromAbbr(this.salaryCurrencyAbbr)
            ),
            workTerritory = mapWorkTerritoryFromString(this.workTerritory),
            logoUrl = this.logoUrl,
            employer = this.employer,
            industry = Industry(null, "") // заглушка
        )
    }

    fun mapWorkTerritoryFromString(workTerritoryString: String): WorkTerritory {

        val country = Country(
            countryId = 0,
            countryName = "Россия" // Значение по умолчанию
        )

        val regionWork = RegionWork(
            regionId = null,
            regionName = workTerritoryString,
            country = country
        )

        return WorkTerritory(regionWork, country)
    }

    fun mapVacancyEntityToVacancyShort(
        vacanciesEntity: List<VacancyEntity>
    ): List<VacancyShort> {
        return vacanciesEntity.map { it.toVacancyShort() }
    }

    fun VacancyDetail.toVacancyEntity(): VacancyEntity {
        return VacancyEntity(
            id = this.vacancyId.toString(),
            vacancyName = this.vacancyName,
            workTerritory = this.workTerritory.regionWork?.regionName.toString(),
            salaryFrom = this.salary.salaryFrom,
            salaryTo = this.salary.salaryTo,
            salaryCurrencyAbbr = this.salary.salaryCurrency.abbr,
            logoUrl = this.logoUrl,
            employer = this.employer,
            description = this.description.toString(),
            address = this.address,
            keySkills = this.keySkills,
            employment = this.employment.employment,
            experience = this.experience,
            workFormat = this.employment.workFormat,
            vacancyUrl = this.vacancyUrl,
        )
    }
}
