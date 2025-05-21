package ru.practicum.android.diploma.search.mapper

import ru.practicum.android.diploma.common.domain.models.Currency
import ru.practicum.android.diploma.common.domain.models.Salary
import ru.practicum.android.diploma.search.data.dto.SalaryDto

object SalaryResponseMapper {
    fun map(salaryDto: SalaryDto?): Salary? {
        if (salaryDto == null) return null

        val currency = Currency.currencyFromAbbr(salaryDto.currency) ?: Currency.RUSSIAN_RUBLE

        return Salary(
            salaryFrom = salaryDto.from,
            salaryTo = salaryDto.to,
            salaryCurrency = currency
        )
    }
}
