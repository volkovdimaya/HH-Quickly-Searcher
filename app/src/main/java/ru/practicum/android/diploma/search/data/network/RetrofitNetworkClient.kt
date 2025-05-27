package ru.practicum.android.diploma.search.data.network

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import ru.practicum.android.diploma.common.data.dto.Response
import ru.practicum.android.diploma.countries.data.dto.CountryResponse
import ru.practicum.android.diploma.industries.data.dto.IndustriesRequest
import ru.practicum.android.diploma.industries.data.dto.IndustriesResponse
import ru.practicum.android.diploma.regions.data.dto.RegionRequest
import ru.practicum.android.diploma.regions.data.dto.RegionsResponse
import ru.practicum.android.diploma.search.data.dto.VacanciesRequest
import ru.practicum.android.diploma.search.data.dto.VacancyDetailsRequest
import ru.practicum.android.diploma.search.data.dto.toMap

class RetrofitNetworkClient(private val hhApiService: HhApiService) : NetworkClient {

    companion object {
        private const val TAG = "RetrofitNetworkClient"
        private const val SUCCESS_CODE = 200
        private const val BAD_REQUEST_CODE = 400
        private const val FORBIDDEN_CODE = 403
        private const val NOT_FOUND_CODE = 404
        private const val TOO_MANY_REQUESTS_CODE = 429
        private const val INTERNAL_ERROR_CODE = 500
    }

    override suspend fun doRequest(dto: Any): Response {
        return withContext(Dispatchers.IO) {
            when (dto) {
                is VacanciesRequest -> handleVacanciesRequest(dto)
                is VacancyDetailsRequest -> handleVacancyDetailsRequest(dto)
                is IndustriesRequest -> handleIndustriesRequest()
                is RegionRequest -> handleAreasRequest(dto)
                else -> handleUnknownRequest()
            }
        }
    }

    private suspend fun handleVacanciesRequest(dto: VacanciesRequest): Response {
        return try {
            val response = hhApiService.searchVacancies(dto.toMap())
            response.apply { resultCode = SUCCESS_CODE }
        } catch (e: HttpException) {
            Log.d(TAG, e.message.toString())
            handleVacanciesError(e)
        }
    }

    private suspend fun handleVacancyDetailsRequest(dto: VacancyDetailsRequest): Response {
        return try {
            val response = hhApiService.getVacancy(dto.id)
            response.apply { resultCode = SUCCESS_CODE }
        } catch (e: HttpException) {
            Log.d(TAG, e.message.toString())
            handleVacancyDetailsError(e)
        }
    }

    private suspend fun handleIndustriesRequest(): Response {
        return try {
            val response = IndustriesResponse(categories = hhApiService.getIndustries())
            response.apply { resultCode = SUCCESS_CODE }
        } catch (e: HttpException) {
            Log.d(TAG, e.message.toString())
            Response().apply { resultCode = INTERNAL_ERROR_CODE }
        }
    }

    private suspend fun handleAreasRequest(dto: RegionRequest): Response {
        return try {
            val areas = if (dto.countryId != null) {
                val countryArea = hhApiService.getAreaById(dto.countryId.toString())
                countryArea.areas ?: emptyList()
            } else {
                hhApiService.getAreas()
            }
            val response = RegionsResponse(regions = areas)
            response.apply { resultCode = SUCCESS_CODE }
        } catch (e: HttpException) {
            Log.d(TAG, e.message.toString())
            Response().apply { resultCode = INTERNAL_ERROR_CODE }
        }
    }

    private fun handleUnknownRequest(): Response {
        return Response().apply { resultCode = BAD_REQUEST_CODE }
    }

    private fun handleVacanciesError(e: HttpException): Response {
        val errorCode = when {
            e.message?.contains("400") == true -> BAD_REQUEST_CODE
            e.message?.contains("403") == true -> FORBIDDEN_CODE
            e.message?.contains("404") == true -> NOT_FOUND_CODE
            else -> INTERNAL_ERROR_CODE
        }
        return Response().apply { resultCode = errorCode }
    }

    private fun handleVacancyDetailsError(e: HttpException): Response {
        val errorCode = when {
            e.message?.contains("403") == true -> FORBIDDEN_CODE
            e.message?.contains("404") == true -> NOT_FOUND_CODE
            e.message?.contains("429") == true -> TOO_MANY_REQUESTS_CODE
            else -> INTERNAL_ERROR_CODE
        }
        return Response().apply { resultCode = errorCode }
    }
}
