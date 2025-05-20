package ru.practicum.android.diploma.common.ui.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FilterParameters(
    var countryId: Int? = null,
    var regionId: Int? = null,
    var industryId: Int? = null,
    var salary: Int? = null,
    var onlyWithSalary: Boolean = false,
) : Parcelable
