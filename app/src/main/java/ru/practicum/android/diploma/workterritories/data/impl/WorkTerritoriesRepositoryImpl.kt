package ru.practicum.android.diploma.workterritories.data.impl

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import ru.practicum.android.diploma.common.data.db.AppDatabase
import ru.practicum.android.diploma.common.domain.models.WorkTerritory
import ru.practicum.android.diploma.favorites.data.local.LocalClient
import ru.practicum.android.diploma.filters.data.entity.FilterParametersEntity
import ru.practicum.android.diploma.industries.data.impl.IndustriesRepositoryImpl.Companion.INTERNAL_ERROR_CODE
import ru.practicum.android.diploma.workterritories.domain.api.WorkTerritoriesRepository
import ru.practicum.android.diploma.workterritories.mapper.WorkTerritoriesMapper.toWorkTerritory

class WorkTerritoriesRepositoryImpl(
    private val appDatabase: AppDatabase,
    private val localClient: LocalClient
) : WorkTerritoriesRepository {

    override fun getWorkTerritories(): Flow<WorkTerritory> {

        return appDatabase.filterUpdateParametersDao().observeFilterParameters()
            .map { it?.toWorkTerritory( ) ?: WorkTerritory()}

    }

    override fun createfilt(): Flow<Boolean> = flow {
        val filterParametersEntity =
            FilterParametersEntity(countryId = 5555, countryName = "kkkkkkkk", )
        val response = localClient.doUpdate(filterParametersEntity) {
            appDatabase.industryDao().updateCountryParameter(filterParametersEntity)
        }
        if (response.resultCode == INTERNAL_ERROR_CODE) {
            error("error")
        }

        val filterParametersEntity2 =
            FilterParametersEntity(regionId = 3333, regionName = "vvvvvvvvv", )
        val response2 = localClient.doUpdate(filterParametersEntity) {
            appDatabase.industryDao().updateRegionParameter(filterParametersEntity2)
        }
        if (response.resultCode == INTERNAL_ERROR_CODE) {
            error("error")
        }

        emit(true)
    }.flowOn(Dispatchers.IO)

    override fun deleteRegionFilter(): Flow<Int> {
        TODO("Not yet implemented")
    }

    override fun deleteCountryFilter(): Flow<Int> {
        TODO("Not yet implemented")
    }
}
