package ru.practicum.android.diploma.vacancy.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.vacancy.domain.api.ExternalNavigator
import ru.practicum.android.diploma.favorites.domain.api.FavoritesRepository
import ru.practicum.android.diploma.vacancy.domain.api.VacancyDetailsInteractor
import ru.practicum.android.diploma.vacancy.domain.api.VacancyDetailsRepository
import ru.practicum.android.diploma.vacancy.domain.models.OverallDetailsResponse
import ru.practicum.android.diploma.vacancy.domain.models.VacancyDetail

class VacancyDetailsInteractorImpl(
    private val repository: VacancyDetailsRepository,
    private val favoritesRepository: FavoritesRepository,
    private val navigator: ExternalNavigator
) : VacancyDetailsInteractor {

    override fun getVacancyDetails(vacancyId: String, isFavourite: Boolean): Flow<OverallDetailsResponse> {
        return repository.getVacancyDetails(vacancyId, isFavourite)
    }

    override fun isVacancyFavourite(vacancyId: String): Flow<Boolean> {
        return repository.isVacancyFavourite(vacancyId)
    }

    override fun addFavourite(vacancyDetail: VacancyDetail): Flow<Int> {
        return favoritesRepository.insertFavoriteVacancy(vacancyDetail)
    }

    override fun deleteFavourite(vacancyDetail: VacancyDetail): Flow<Int> {
        return favoritesRepository.deleteFavoriteVacancy(vacancyDetail)
    }

    override fun shareVacancy(link: String) {
        navigator.shareString(link)
    }

}
