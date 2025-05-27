package ru.practicum.android.diploma.favorites.data.local

import ru.practicum.android.diploma.common.data.dto.Response
import ru.practicum.android.diploma.favorites.data.entity.VacancyEntity

class FavoritesResponse(
    val items: List<VacancyEntity> = emptyList()
) : Response()
