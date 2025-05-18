package ru.practicum.android.diploma.favorites.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.common.domain.models.VacancyShort
import ru.practicum.android.diploma.favorites.domain.api.FavoritesRepository
import ru.practicum.android.diploma.favorites.domain.interactors.FavoritesInteractor

class FavoritesInteractorImpl(private val favoritesRepository: FavoritesRepository) : FavoritesInteractor {

    override fun loadFavorites(): Flow<Pair<Int, List<VacancyShort>>> {
        return favoritesRepository.loadFavorites()
    }

}
