package ru.practicum.android.diploma.industries.data.dto

import ru.practicum.android.diploma.industries.data.entity.IndustryEntity
import ru.practicum.android.diploma.search.data.dto.Response

class IndustryLocalResponse(
    val industries: List<IndustryEntity> = emptyList()
) : Response()
