package ru.practicum.android.diploma.favorites.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.common.domain.models.VacancyShort
import ru.practicum.android.diploma.vacancy.domain.models.VacancyDetail

interface FavoritesRepository {
    suspend fun notifyDatabaseChanged()
    fun loadFavorites(): Flow<Pair<Int, List<VacancyShort>>>
    fun insertFavoriteVacancy(vacancyDetail: VacancyDetail): Flow<Int>
    fun deleteFavoriteVacancy(vacancyDetail: VacancyDetail): Flow<Int>
}
