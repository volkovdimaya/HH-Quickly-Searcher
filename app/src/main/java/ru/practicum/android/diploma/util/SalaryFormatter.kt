package ru.practicum.android.diploma.util

import android.content.Context
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.common.domain.models.Salary

fun salaryFormatter(salary: Salary, context: Context): String {
    return buildString {
        salary.salaryFrom?.let {
            this.append("от ")
            this.append(DecimalNumberFormatter.formatNumber(it))
            this.append(" ")
        }
        salary.salaryTo?.let {
            this.append("до ")
            this.append(DecimalNumberFormatter.formatNumber(it))
            this.append(" ")
        }

        if (this.isNotEmpty()) {
            this.append(salary.salaryCurrency.symbol)
        } else {
            this.append(context.getString(R.string.salary_is_not_said))
        }
    }
}
