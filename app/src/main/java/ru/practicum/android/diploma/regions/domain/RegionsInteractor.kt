package ru.practicum.android.diploma.regions.domain

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.regions.domain.models.Region

interface RegionsInteractor {

    fun loadRegions(countryId: String? = null): Flow<Pair<Int, List<Region>>>
    fun getSearchList(text: String): Flow<Pair<Int, List<Region>>>
    fun getLocalRegionsList(): Flow<Pair<Int, List<Region>>>
    suspend fun clearTableDb()
    fun saveFilterParameter(item: Region): Flow<Int>
    suspend fun getCurrentCountryId(): Int?
}
