package ru.practicum.android.diploma.regions.data.impl

import android.app.Application
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.common.data.db.AppDatabase
import ru.practicum.android.diploma.common.data.dto.Response
import ru.practicum.android.diploma.favorites.data.local.LocalClient
import ru.practicum.android.diploma.filters.data.entity.FilterParametersEntity
import ru.practicum.android.diploma.regions.data.dto.AreaDto
import ru.practicum.android.diploma.regions.data.dto.RegionRequest
import ru.practicum.android.diploma.regions.data.dto.RegionsLocalResponse
import ru.practicum.android.diploma.regions.data.dto.RegionsResponse
import ru.practicum.android.diploma.regions.domain.api.RegionsRepository
import ru.practicum.android.diploma.regions.domain.models.Region
import ru.practicum.android.diploma.regions.mapper.AreaMapper
import ru.practicum.android.diploma.regions.mapper.AreaMapper.toEntity
import ru.practicum.android.diploma.regions.mapper.AreaMapper.toRegion
import ru.practicum.android.diploma.search.data.network.NetworkClient
import ru.practicum.android.diploma.util.isConnectedInternet
import ru.practicum.android.diploma.workterritories.data.entity.AreaEntity

class RegionsRepositoryImpl(
    private val localClient: LocalClient,
    private val appDatabase: AppDatabase,
    private val networkClient: NetworkClient,
    private val application: Application
) : RegionsRepository {

    companion object {
        const val INTERNAL_ERROR_CODE = 500
        private const val BAD_REQUEST_CODE = 400
    }

    override fun loadRegions(countryId: String?): Flow<Pair<Int, List<Region>>> = flow {
        val negativeResponseNetwork = !isConnectedInternet(application)
        val response = if (negativeResponseNetwork) {
            Response().apply { resultCode = INTERNAL_ERROR_CODE }
        } else {
            getAreasFromNetwork(countryId)
        }
        val result = if (response is RegionsResponse) {
            val areaDtoList = AreaMapper.flattenAreaDtoList(response.regions).sortedBy { it.name }
            saveAreas(areaDtoList)
            Pair(response.resultCode, AreaMapper.mapAreaDtoToRegion(areaDtoList))
        } else {
            Pair(response.resultCode, listOf())
        }
        emit(result)
    }.flowOn(Dispatchers.IO)

    override fun getSearchList(text: String): Flow<Pair<Int, List<Region>>> = flow {
        val response: RegionsLocalResponse = localClient.doRead {
            RegionsLocalResponse(
                areas = appDatabase.areaDao().searchAreas(text)
            )
        }
        val mappedList = if (response.resultCode != INTERNAL_ERROR_CODE) {
            response.areas.map { it.toRegion() }.sortedBy { it.regionName }
        } else {
            listOf()
        }
        emit(Pair(response.resultCode, mappedList))
    }.flowOn(Dispatchers.IO)

    override fun getLocalRegionsList(): Flow<Pair<Int, List<Region>>> = flow {
        val response: RegionsLocalResponse = localClient.doRead {
            RegionsLocalResponse(
                areas = appDatabase.areaDao().getAreas()
            )
        }
        val mappedList = if (response.resultCode != INTERNAL_ERROR_CODE) {
            response.areas.map { it.toRegion() }.sortedBy { it.regionName }
        } else {
            listOf()
        }
        emit(Pair(response.resultCode, mappedList))
    }.flowOn(Dispatchers.IO)

    override suspend fun clearTableDb() {
        withContext(Dispatchers.IO) {
            appDatabase.areaDao().clearTable()
        }
    }

    override fun insertFilterParameter(item: Region): Flow<Int> = flow {
        val currentFilter = appDatabase.areaDao().getParameters()

        var countryId = currentFilter?.countryId
        var countryName = currentFilter?.countryName

        if (countryId.toString().isNullOrBlank()) {
            val country = findCountryForArea(item.regionId)
            if (country != null) {
                countryId = country.areaId
                countryName = country.areaName
            }
        }

        val updatedFilterParameters = FilterParametersEntity(
            filtersId = currentFilter?.filtersId ?:"1",
            countryId = countryId,
            countryName = countryName,
            regionId = item.regionId.toInt(),
            regionName = item.regionName,
            industryId = currentFilter?.industryId,
            industryName = currentFilter?.industryName,
            salary = currentFilter?.salary,
            onlyWithSalary = currentFilter?.onlyWithSalary ?: false
        )

        val response = localClient.doUpdate(updatedFilterParameters) {
            appDatabase.filterParametersDao().saveFilters(updatedFilterParameters)
        }

        if (response.resultCode == INTERNAL_ERROR_CODE) {
            error("error")
        }
        emit(response.resultCode)
    }.flowOn(Dispatchers.IO)

    override suspend fun getCurrentCountryId(): String? {
        return withContext(Dispatchers.IO) {
            val currentFilter = appDatabase.areaDao().getParameters()
            currentFilter?.countryId.toString()
        }
    }

    private suspend fun getAreasFromNetwork(countryId: String?): Response {
        val response = networkClient.doRequest(RegionRequest(countryId))
        return if (response is RegionsResponse) {
            response
        } else {
            Response().apply { resultCode = BAD_REQUEST_CODE }
        }
    }

    private suspend fun saveAreas(areas: List<AreaDto>) {
        val areaEntities = areas.map { it.toEntity() }

        val response = localClient.doWrite(areaEntities) {
            appDatabase.areaDao().insertAreas(areaEntities)
        }
        if (response.resultCode == INTERNAL_ERROR_CODE) {
            error("error")
        }
    }

    private suspend fun findCountryForArea(areaId: String): AreaEntity? {
        var area = appDatabase.areaDao().getAreaById(areaId)
        var parentArea: AreaEntity?

        while (area?.parentId != null) {
            parentArea = appDatabase.areaDao().getAreaById(area.parentId.toString())
            if (parentArea != null) {
                area = parentArea
            } else {
                break
            }
        }
        return area
    }
}
