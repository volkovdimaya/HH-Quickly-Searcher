package ru.practicum.android.diploma.filters.ui.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import ru.practicum.android.diploma.filters.domain.models.FilterParametersDomain

@Parcelize
data class FilterParametersUi(
    val countryId: Int? = null,
    val regionId: Int? = null,
    val industryId: Int? = null,
    val salary: Int? = null,
    val onlyWithSalary: Boolean = false,
) : Parcelable

fun FilterParametersUi?.toDomain(): FilterParametersDomain? {
    return this?.let {
        FilterParametersDomain(
            countryId = it.countryId,
            regionId = it.regionId,
            industryId = it.industryId,
            salary = it.salary,
            onlyWithSalary = it.onlyWithSalary
        )
    }
}
