package ru.practicum.android.diploma.workterritories.presentation.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.common.domain.models.Country
import ru.practicum.android.diploma.regions.domain.models.Region

interface InteractorWorkTerritories {
    fun getCountry(): Flow<Country?>
    suspend fun getRegion(): Flow<Region?>
    suspend fun deleteCountry()
    suspend fun deleteRegion()
}
