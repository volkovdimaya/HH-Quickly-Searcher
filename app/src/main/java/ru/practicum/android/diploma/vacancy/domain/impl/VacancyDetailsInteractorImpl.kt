package ru.practicum.android.diploma.vacancy.domain.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.vacancy.domain.api.VacancyDetailsInteractor
import ru.practicum.android.diploma.vacancy.domain.models.VacancyDetail

class VacancyDetailsInteractorImpl: VacancyDetailsInteractor {

    override fun getVacancyDetails(vacancyId: Int, isFavourite: Boolean): Flow<VacancyDetail> = flow {
        if (isFavourite) {
            // get vacancy from db
        } else {
            // get vacancy from server
        }
    }

    override fun isVacancyFavourite(vacancyId: Int): Flow<Boolean> {
        TODO("Not yet implemented")
    }

}
