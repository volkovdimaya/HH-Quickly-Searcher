package ru.practicum.android.diploma.vacancy.data.impl

import android.app.Application
import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.common.data.db.AppDatabase
import ru.practicum.android.diploma.search.data.dto.VacancyDetailsRequest
import ru.practicum.android.diploma.search.data.dto.VacancyDetailsResponse
import ru.practicum.android.diploma.search.data.network.NetworkClient
import ru.practicum.android.diploma.util.isConnectedInternet
import ru.practicum.android.diploma.vacancy.domain.api.VacancyDetailsRepository
import ru.practicum.android.diploma.vacancy.domain.models.OverallDetailsResponse
import ru.practicum.android.diploma.vacancy.mapper.VacancyDetailsMapper

class VacancyDetailsRepositoryImpl(
    private val networkClient: NetworkClient,
    private val dataBase: AppDatabase,
    private val application: Application
) : VacancyDetailsRepository {

    override fun getVacancyDetails(vacancyId: String, isFavourite: Boolean): Flow<OverallDetailsResponse> = flow {
        if (isFavourite) {
            emit(getDetailsFromDb(vacancyId))
        } else if (isConnectedInternet(application)) {
            emit(getDetailsFromNetwork(vacancyId))
        } else {
            emit(OverallDetailsResponse(BAD_REQUEST_CODE))
        }
    }

    override fun isVacancyFavourite(vacancyId: String): Flow<Boolean> = flow {
        val idList = dataBase.vacancyDao().getFavoritesId()
        val isFavourite = vacancyId in idList
        emit(isFavourite)
    }

    private suspend fun getDetailsFromDb(vacancyId: String): OverallDetailsResponse {
        val vacancyEntity = dataBase.vacancyDao().getVacancyById(vacancyId)
        val vacancy = VacancyDetailsMapper.mapFromEntity(vacancyEntity)
        val response = OverallDetailsResponse(SUCCESS_CODE).apply {
            vacancyDetail = listOf(vacancy)
        }
        return response
    }

    private suspend fun getDetailsFromNetwork(vacancyId: String): OverallDetailsResponse {
        Log.d("666", vacancyId)
        val networkResponse = networkClient.doRequest(VacancyDetailsRequest(vacancyId))
        val response = OverallDetailsResponse(networkResponse.resultCode)
        if (networkResponse is VacancyDetailsResponse && networkResponse.vacancy != null) {
            response.apply {
                vacancyDetail = listOf(
                    VacancyDetailsMapper.mapFromDto(networkResponse.vacancy)
                )
                Log.d("666", vacancyDetail.toString())
            }
        }
        return response
    }

    companion object {
        private const val BAD_REQUEST_CODE = 400
        private const val SUCCESS_CODE = 200
    }
}
