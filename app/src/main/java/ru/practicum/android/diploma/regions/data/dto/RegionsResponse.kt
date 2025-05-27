package ru.practicum.android.diploma.regions.data.dto

import ru.practicum.android.diploma.common.data.dto.Response

class RegionsResponse(
    val regions: List<AreaDto> = emptyList()
) : Response()
