package ru.practicum.android.diploma.search.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.common.domain.models.VacancyShort
import ru.practicum.android.diploma.common.ui.models.FilterParameters

interface SearchRepository {

    fun searchVacancies(text: String, filters: FilterParameters?): Flow<List<VacancyShort>>

}
