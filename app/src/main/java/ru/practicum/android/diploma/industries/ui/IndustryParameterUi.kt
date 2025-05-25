package ru.practicum.android.diploma.industries.ui

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import ru.practicum.android.diploma.industries.domain.models.Industry

@Parcelize
data class IndustryParameterUi(
    val industryId: String,
    val industryName: String
) : Parcelable

fun IndustryParameterUi?.toDomain(): Industry? {
    return this?.let {
        Industry(
            industryId = this.industryId,
            industryName = this.industryName
        )
    }
}

fun Industry.toParcelable(): IndustryParameterUi {
    return IndustryParameterUi(
        industryId = this.industryId,
        industryName = this.industryName
    )
}
