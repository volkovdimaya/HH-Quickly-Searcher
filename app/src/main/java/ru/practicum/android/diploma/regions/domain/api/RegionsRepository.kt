package ru.practicum.android.diploma.regions.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.regions.domain.models.Region

interface RegionsRepository {

    fun loadRegions(countryId: String? = null): Flow<Pair<Int, List<Region>>>
    fun getSearchList(text: String): Flow<Pair<Int, List<Region>>>
    fun getLocalRegionsList(): Flow<Pair<Int, List<Region>>>
    fun insertFilterParameter(item: Region): Flow<Int>
    suspend fun clearTableDb()
    suspend fun getCurrentCountryId(): String?
}
