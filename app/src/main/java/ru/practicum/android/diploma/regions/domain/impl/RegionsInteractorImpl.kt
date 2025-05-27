package ru.practicum.android.diploma.regions.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.regions.domain.RegionsInteractor
import ru.practicum.android.diploma.regions.domain.api.RegionsRepository
import ru.practicum.android.diploma.regions.domain.models.Region

class RegionsInteractorImpl(private val regionsRepository: RegionsRepository) : RegionsInteractor {

    override fun loadRegions(countryId: String?): Flow<Pair<Int, List<Region>>> {
        return regionsRepository.loadRegions(countryId)
    }

    override fun getSearchList(text: String): Flow<Pair<Int, List<Region>>> {
        return regionsRepository.getSearchList(text)
    }

    override fun getLocalRegionsList(): Flow<Pair<Int, List<Region>>> {
        return regionsRepository.getLocalRegionsList()
    }

    override fun saveFilterParameter(item: Region): Flow<Int> {
        return regionsRepository.insertFilterParameter(item)
    }

    override suspend fun clearTableDb() {
        regionsRepository.clearTableDb()
    }

    override suspend fun getCurrentCountryId(): String? {
        return regionsRepository.getCurrentCountryId()
    }
}
