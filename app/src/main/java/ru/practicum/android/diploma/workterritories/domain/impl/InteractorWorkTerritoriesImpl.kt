package ru.practicum.android.diploma.workterritories.domain.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.practicum.android.diploma.common.domain.models.Country
import ru.practicum.android.diploma.filters.domain.api.FilterParametersRepository
import ru.practicum.android.diploma.filters.domain.api.FilterParametersType
import ru.practicum.android.diploma.regions.domain.models.Region
import ru.practicum.android.diploma.workterritories.presentation.api.InteractorWorkTerritories

class InteractorWorkTerritoriesImpl(val repDb: FilterParametersRepository) : InteractorWorkTerritories {
    override fun getCountry(): Flow<Country?> =
        repDb.getFilterParameters()
            .map {
                if (it.countryId == null || it.countryName == null) {
                    return@map null
                } else {
                    Country(
                        countryId = it.countryId,
                        countryName = it.countryName
                    )
                }
            }


    override suspend fun getRegion(): Flow<Region?> =
        repDb.getFilterParameters()
            .map {
                if (it.regionId == null || it.regionName == null) {
                    return@map null
                } else {
                    Region(
                        regionId = it.regionId.toString(),
                        regionName = it.regionName
                    )
                }
            }

    override suspend fun deleteCountry() {
        repDb.saveFilterParameters(
            FilterParametersType.Country()
        )
    }

    override suspend fun deleteRegion() {
        repDb.saveFilterParameters(
            FilterParametersType.Region()
        )
    }
}
