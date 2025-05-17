package ru.practicum.android.diploma.search.presentation.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.common.domain.models.VacancyShort
import ru.practicum.android.diploma.search.data.dto.VacanciesRequest
import ru.practicum.android.diploma.search.domain.models.DataConsumer

interface InteractorSearchListVacancy {
    fun loadVacancy(vacanciesRequest : VacanciesRequest): Flow<DataConsumer<List<VacancyShort>>>
}
