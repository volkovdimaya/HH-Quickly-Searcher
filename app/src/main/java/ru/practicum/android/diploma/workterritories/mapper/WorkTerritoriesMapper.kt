package ru.practicum.android.diploma.workterritories.mapper

import ru.practicum.android.diploma.common.domain.models.WorkTerritory
import ru.practicum.android.diploma.countries.mapper.CountryMapper.toCountry
import ru.practicum.android.diploma.favorites.data.local.AreaResponse
import ru.practicum.android.diploma.favorites.data.local.LocalClient
import ru.practicum.android.diploma.regions.mapper.RegionMapper.toRegion
import ru.practicum.android.diploma.search.data.dto.Response

object WorkTerritoriesMapper {

    private const val INTERNAL_ERROR_CODE = 500
    private const val ERROR = "LocalDbClient error"

    suspend fun createWorkTerritory(localClient: LocalClient, areaId: String): WorkTerritory {
        val response = Response()
        val workTerritory = if (response.resultCode == INTERNAL_ERROR_CODE) {
            error(ERROR)
        } else {
            WorkTerritory(
                (response as AreaResponse).areaEntity.toRegion(),
                (response as AreaResponse).areaEntity.toCountry()
            )
        }
        return workTerritory
    }
}
