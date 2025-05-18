package ru.practicum.android.diploma.vacancy.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.vacancy.domain.api.VacancyDetailsInteractor
import ru.practicum.android.diploma.vacancy.domain.models.VacancyDetail

class VacancyDetailsInteractorImpl: VacancyDetailsInteractor {

    override fun getVacancyDetails(vacancyId: Int): Flow<VacancyDetail> {
        TODO("Not yet implemented")
    }

    override fun isVacancyFavourite(vacancyId: Int): Flow<Boolean> {
        TODO("Not yet implemented")
    }

}
