package ru.practicum.android.diploma.search.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.common.domain.models.Vacancy
import ru.practicum.android.diploma.common.domain.models.VacancyShort
import ru.practicum.android.diploma.search.data.dto.VacanciesRequest
import ru.practicum.android.diploma.util.Resource
import ru.practicum.android.diploma.vacancy.domain.models.VacancyDetail

interface VacancyRepository {

    fun searchVacancies(
        vacanciesRequest : VacanciesRequest
    ): Flow<Resource<List<VacancyShort>>>

    fun getVacancyDetails(id: String): Flow<VacancyDetail?>

}
