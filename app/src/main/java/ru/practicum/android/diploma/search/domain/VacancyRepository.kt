package ru.practicum.android.diploma.search.domain

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.common.domain.models.Vacancy
import ru.practicum.android.diploma.vacancy.domain.models.VacancyDetail

interface VacancyRepository {

    fun searchVacancy(
        text: String,
        area: String? = null,
        professionalRole: String? = null,
        industry: String? = null,
        salary: Int? = null,
        onlyWithSalary: Boolean = false,
        page: Int = 0,
        perPage: Int = 20
    ): Flow<List<Vacancy>?>

    fun getVacancyDetails(id: String): Flow<VacancyDetail?>

}
