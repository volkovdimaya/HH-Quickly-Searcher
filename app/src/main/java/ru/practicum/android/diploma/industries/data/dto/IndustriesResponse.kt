package ru.practicum.android.diploma.industries.data.dto

import ru.practicum.android.diploma.common.data.dto.Response

class IndustriesResponse(
    val categories: List<IndustryCategoryDto> = emptyList()
) : Response()
