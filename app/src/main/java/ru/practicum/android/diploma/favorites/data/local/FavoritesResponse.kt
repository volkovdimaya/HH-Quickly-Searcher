package ru.practicum.android.diploma.favorites.data.local

import ru.practicum.android.diploma.common.data.models.VacancyWithWorkTerritory
import ru.practicum.android.diploma.search.data.dto.Response

class FavoritesResponse(
    val items: List<VacancyWithWorkTerritory> = emptyList()
) : Response()
