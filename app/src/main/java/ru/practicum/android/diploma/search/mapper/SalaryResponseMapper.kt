package ru.practicum.android.diploma.search.mapper

import ru.practicum.android.diploma.common.domain.models.Salary
import ru.practicum.android.diploma.search.data.dto.SalaryDto

object SalaryResponseMapper {
    fun map(salaryDto : SalaryDto?) : Salary? {
        if (salaryDto == null) return null
        return Salary(
            from = salaryDto.from,
            to = salaryDto.to,
            currency = salaryDto.currency,
        )
    }
}
