package ru.practicum.android.diploma.search.data.dto

class VacanciesResponse(
    val items: List<VacancyDto> = emptyList(),
    val found: Int = 0,
    val pages: Int = 0,
    val page: Int = 0,
    val perPage: Int = 0
) : Response()
