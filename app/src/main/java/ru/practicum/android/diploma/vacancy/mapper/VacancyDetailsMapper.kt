package ru.practicum.android.diploma.vacancy.mapper

import ru.practicum.android.diploma.common.domain.models.Currency
import ru.practicum.android.diploma.common.domain.models.Salary
import ru.practicum.android.diploma.favorites.data.entity.VacancyEntity
import ru.practicum.android.diploma.search.data.dto.AddressDto
import ru.practicum.android.diploma.search.data.dto.VacancyDetailsDto
import ru.practicum.android.diploma.vacancy.domain.models.EmploymentType
import ru.practicum.android.diploma.vacancy.domain.models.VacancyDetail

object VacancyDetailsMapper {

    fun mapFromEntity(vacancyEntity: VacancyEntity): VacancyDetail {
        return VacancyDetail(
            vacancyEntity.id,
            vacancyEntity.vacancyName,
            vacancyEntity.workTerritory,
            Salary(
                vacancyEntity.salaryFrom,
                vacancyEntity.salaryTo,
                Currency.currencyFromAbbr(vacancyEntity.salaryCurrencyAbbr)!!
            ),
            vacancyEntity.logoUrl,
            vacancyEntity.employer,
            vacancyEntity.description,
            vacancyEntity.address,
            EmploymentType(
                vacancyEntity.employment,
                vacancyEntity.workFormat
            ),
            if (vacancyEntity.keySkills.isNullOrEmpty()) {
                emptyList()
            } else {
                vacancyEntity.keySkills
            },
            vacancyEntity.experience,
            vacancyEntity.vacancyUrl
        )
    }

    fun mapToEntity(vacancyDetail: VacancyDetail): VacancyEntity {
        return VacancyEntity(
            vacancyDetail.vacancyId,
            vacancyDetail.vacancyName,
            vacancyDetail.workTerritory,
            vacancyDetail.salary.salaryFrom,
            vacancyDetail.salary.salaryTo,
            vacancyDetail.salary.salaryCurrency.abbr,
            vacancyDetail.logoUrl,
            vacancyDetail.employer,
            vacancyDetail.description,
            vacancyDetail.address,
            vacancyDetail.keySkills,
            vacancyDetail.employment.employment,
            vacancyDetail.employment.workFormat,
            vacancyDetail.experience,
            vacancyDetail.vacancyUrl
        )
    }

    fun mapFromDto(dto: VacancyDetailsDto): VacancyDetail {
        return VacancyDetail(
            dto.id,
            dto.name,
            dto.area.name,
            Salary(
                dto.salaryRange?.from,
                dto.salaryRange?.to,
                Currency.currencyFromAbbr(dto.salaryRange?.currency ?: "")
            ),
            dto.employer?.logoUrls?.original ?: "",
            dto.employer?.name ?: "",
            dto.description,
            makeAddressStr(dto.address),
            EmploymentType(
                dto.employmentForm?.name ?: "",
                dto.workFormat?.name ?: ""
            ),
            dto.keySkills,
            dto.experience?.name ?: "",
            dto.alternateUrl
        )
    }

    private fun makeAddressStr(address: AddressDto?): String {
        if (address == null) {
            return ""
        }
        var str = ""
        if (address.city != null) {
            str += address.city
        }
        if (address.city != null && address.street != null) {
            str += ", "
        }
        if (address.street != null) {
            str += address.street
            if (address.building != null) {
                str += ", " + address.building
            }
        }
        return str
    }

}
