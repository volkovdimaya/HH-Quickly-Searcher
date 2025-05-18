package ru.practicum.android.diploma.vacancy.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.vacancy.domain.models.VacancyDetail

interface VacancyDetailsInteractor {

    fun getVacancyDetails(vacancyId: Int, isFavourite: Boolean): Flow<VacancyDetail>
    fun isVacancyFavourite(vacancyId: Int): Flow<Boolean>
}
