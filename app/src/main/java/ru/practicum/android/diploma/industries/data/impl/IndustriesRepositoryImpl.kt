package ru.practicum.android.diploma.industries.data.impl

import android.app.Application
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.common.data.db.AppDatabase
import ru.practicum.android.diploma.favorites.data.local.LocalClient
import ru.practicum.android.diploma.industries.data.dto.IndustriesRequest
import ru.practicum.android.diploma.industries.data.dto.IndustriesResponse
import ru.practicum.android.diploma.industries.data.dto.IndustryDto
import ru.practicum.android.diploma.industries.data.dto.IndustryLocalResponse
import ru.practicum.android.diploma.industries.domain.api.IndustriesRepository
import ru.practicum.android.diploma.industries.domain.models.Industry
import ru.practicum.android.diploma.industries.mapper.IndustryMapper
import ru.practicum.android.diploma.industries.mapper.IndustryMapper.toEntity
import ru.practicum.android.diploma.industries.mapper.IndustryMapper.toIndustry
import ru.practicum.android.diploma.search.data.dto.Response
import ru.practicum.android.diploma.search.data.network.NetworkClient
import ru.practicum.android.diploma.util.isConnectedInternet

class IndustriesRepositoryImpl(
    private val localClient: LocalClient,
    private val appDatabase: AppDatabase,
    private val networkClient: NetworkClient,
    private val application: Application
) : IndustriesRepository {

    companion object {
        const val INTERNAL_ERROR_CODE = 500
        private const val BAD_REQUEST_CODE = 400
    }

    override fun loadIndustries(): Flow<Pair<Int, List<Industry>>> = flow {

        val response = if (!(isConnectedInternet(application))) {
            Response().apply { resultCode = INTERNAL_ERROR_CODE }
        } else {
            getIndustriesFromNetwork()
        }
        val result = if (response is IndustriesResponse) {
            val industryDtoList =
                IndustryMapper.mapIndustryCategoryDtoToIndustryDto(response.categories).sortedBy { it.name }
            saveIndustry(industryDtoList)
            Pair(response.resultCode, IndustryMapper.mapIndustryDtoToIndustry(industryDtoList))
        } else {
            Pair(response.resultCode, listOf())
        }
        emit(result)
    }.flowOn(Dispatchers.IO)

    override fun getSearchList(text: String): Flow<Pair<Int, List<Industry>>> = flow {
        val response: IndustryLocalResponse = localClient.doRead {
            IndustryLocalResponse(
                industries = appDatabase.industryDao().searchIndustries(text)
            )
        }
        val mappedList = if (response.resultCode != 500) {
            response.industries.map { it.toIndustry() }.sortedBy { it.industryName }
        } else {
            listOf()
        }
        emit(Pair(response.resultCode, mappedList))
    }.flowOn(Dispatchers.IO)

    override fun getLocalIndustryList(): Flow<Pair<Int, List<Industry>>> = flow {
        val response: IndustryLocalResponse = localClient.doRead {
            IndustryLocalResponse(
                industries = appDatabase.industryDao().getIndustries()
            )
        }
        val mappedList = if (response.resultCode != 500) {
            response.industries.map { it.toIndustry() }.sortedBy { it.industryName }
        } else {
            listOf()
        }
        emit(Pair(response.resultCode, mappedList))

    }.flowOn(Dispatchers.IO)

    override suspend fun clearTableDb() {
        withContext(Dispatchers.IO) {
            appDatabase.industryDao().clearTable()
        }
    }

    private suspend fun getIndustriesFromNetwork(): Response {
        val response = networkClient.doRequest(IndustriesRequest())
        return if (response is IndustriesResponse) {
            response
        } else {
            Response().apply { resultCode = BAD_REQUEST_CODE }
        }
    }

    private suspend fun saveIndustry(industries: List<IndustryDto>) {
        val industryEntities = industries.map { it.toEntity() }

        val response = localClient.doWrite(industryEntities) {
            appDatabase.industryDao().insertIndustry(industryEntities)
        }
        if (response.resultCode == INTERNAL_ERROR_CODE) {
            error("error")
            return
        }
    }
}
