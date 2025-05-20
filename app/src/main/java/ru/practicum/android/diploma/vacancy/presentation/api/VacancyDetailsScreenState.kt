package ru.practicum.android.diploma.vacancy.presentation.api

import ru.practicum.android.diploma.vacancy.domain.models.VacancyDetail

sealed class VacancyDetailsScreenState {
    data object Loading : VacancyDetailsScreenState()
    data object ServerError : VacancyDetailsScreenState()
    data object NothingFound : VacancyDetailsScreenState()
    data class Data(
        val vacancyDetails: VacancyDetail,
        val isFavourite: Boolean
    ) : VacancyDetailsScreenState()
}
