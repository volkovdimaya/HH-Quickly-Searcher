package ru.practicum.android.diploma.vacancy.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.vacancy.domain.models.OverallDetailsResponse
import ru.practicum.android.diploma.vacancy.domain.models.VacancyDetail

interface VacancyDetailsInteractor {

    fun getVacancyDetails(vacancyId: String, isFavourite: Boolean): Flow<OverallDetailsResponse>
    fun isVacancyFavourite(vacancyId: String): Flow<Boolean>
    fun addFavourite(vacancyDetail: VacancyDetail): Flow<Int>
    fun deleteFavourite(vacancyDetail: VacancyDetail): Flow<Int>
}
