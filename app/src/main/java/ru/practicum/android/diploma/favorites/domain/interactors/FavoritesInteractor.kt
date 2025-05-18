package ru.practicum.android.diploma.favorites.domain.interactors

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.common.domain.models.VacancyShort

interface FavoritesInteractor {

    fun loadFavorites(): Flow<Pair<Int, List<VacancyShort>>>

}
