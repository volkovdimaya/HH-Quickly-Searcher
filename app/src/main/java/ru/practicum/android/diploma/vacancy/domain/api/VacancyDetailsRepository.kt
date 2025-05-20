package ru.practicum.android.diploma.vacancy.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.vacancy.domain.models.OverallDetailsResponse

interface VacancyDetailsRepository {

    fun getVacancyDetails(vacancyId: String, isFavourite: Boolean): Flow<OverallDetailsResponse>
    fun isVacancyFavourite(vacancyId: String): Flow<Boolean>
    suspend fun addFavourite(vacancyId: String)
    suspend fun deleteFavourite(vacancyId: String)
}
