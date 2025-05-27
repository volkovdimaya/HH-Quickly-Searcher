package ru.practicum.android.diploma.regions.data.dto

import ru.practicum.android.diploma.common.data.dto.Response
import ru.practicum.android.diploma.workterritories.data.entity.AreaEntity

class RegionsLocalResponse(
    val areas: List<AreaEntity> = emptyList()
) : Response()
