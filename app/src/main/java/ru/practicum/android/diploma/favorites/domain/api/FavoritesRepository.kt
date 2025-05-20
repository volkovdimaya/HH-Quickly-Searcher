package ru.practicum.android.diploma.favorites.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.common.domain.models.VacancyShort

interface FavoritesRepository {

    suspend fun notifyDatabaseChanged()
    fun loadFavorites(): Flow<Pair<Int, List<VacancyShort>>>

}
