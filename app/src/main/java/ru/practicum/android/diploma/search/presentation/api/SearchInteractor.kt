package ru.practicum.android.diploma.search.presentation.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.common.domain.models.VacancyShort
import ru.practicum.android.diploma.common.ui.models.FilterParameters

interface SearchInteractor {

    fun searchVacancies(query: String, filters: FilterParameters?): Flow<List<VacancyShort>>
}
