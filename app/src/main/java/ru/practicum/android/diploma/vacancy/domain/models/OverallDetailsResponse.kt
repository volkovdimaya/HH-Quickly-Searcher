package ru.practicum.android.diploma.vacancy.domain.models

class OverallDetailsResponse(
    val resultCode: Int
) {
    var vacancyDetail = emptyList<VacancyDetail>()
}
