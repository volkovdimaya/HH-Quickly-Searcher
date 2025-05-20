package ru.practicum.android.diploma.vacancy.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.vacancy.domain.api.VacancyDetailsInteractor
import ru.practicum.android.diploma.vacancy.domain.api.VacancyDetailsRepository
import ru.practicum.android.diploma.vacancy.domain.models.OverallDetailsResponse

class VacancyDetailsInteractorImpl(
    private val repository: VacancyDetailsRepository
) : VacancyDetailsInteractor {

    override fun getVacancyDetails(vacancyId: String, isFavourite: Boolean) : Flow<OverallDetailsResponse> {
        return repository.getVacancyDetails(vacancyId, isFavourite)
    }

    override fun isVacancyFavourite(vacancyId: String) : Flow<Boolean> {
        return repository.isVacancyFavourite(vacancyId)
    }

    override suspend fun addFavourite(vacancyId: String) {
        repository.addFavourite(vacancyId)
    }

    override suspend fun deleteFavourite(vacancyId: String) {
        repository.deleteFavourite(vacancyId)
    }

}
