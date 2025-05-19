package ru.practicum.android.diploma.favorites.data.local

import ru.practicum.android.diploma.favorites.data.entity.VacancyEntity
import ru.practicum.android.diploma.search.data.dto.Response

class FavoritesResponse(
    val items: List<VacancyEntity> = emptyList(),
    val found: Int = 0,
) : Response()
