package ru.practicum.android.diploma.search.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.common.domain.models.VacancyShort
import ru.practicum.android.diploma.filters.domain.models.FilterParametersDomain
import ru.practicum.android.diploma.vacancy.domain.models.VacancyDetail

interface VacanciesRepository {

    fun searchVacancies(text: String, filters: FilterParametersDomain?): Flow<List<VacancyShort>>

    fun getVacancyDetails(id: Int): Flow<VacancyDetail?>
}
