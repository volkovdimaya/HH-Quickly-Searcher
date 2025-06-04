package ru.practicum.android.diploma.search.domain.models

import ru.practicum.android.diploma.common.domain.models.VacancyShort

data class SearchResult(
    val vacancies: List<VacancyShort>,
    val found: Int,
    val pages: Int,
    val currentPage: Int,
    val resultCode: Int = 0
)
