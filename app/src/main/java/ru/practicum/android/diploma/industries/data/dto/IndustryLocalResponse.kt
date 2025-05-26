package ru.practicum.android.diploma.industries.data.dto

import ru.practicum.android.diploma.common.data.dto.Response
import ru.practicum.android.diploma.industries.data.entity.IndustryEntity

class IndustryLocalResponse(
    val industries: List<IndustryEntity> = emptyList()
) : Response()
