package ru.practicum.android.diploma.search.data.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.industries.data.dto.IndustriesRequest
import ru.practicum.android.diploma.regions.data.dto.AreasRequest
import ru.practicum.android.diploma.search.data.dto.Response
import ru.practicum.android.diploma.search.data.dto.VacanciesRequest
import ru.practicum.android.diploma.search.data.dto.VacancyDetailsRequest

class RetrofitNetworkClient(private val hhApiService: HhApiService) : NetworkClient {

    override suspend fun doRequest(dto: Any): Response {
        return withContext(Dispatchers.IO) {
            when (dto) {
                is VacanciesRequest -> {
                    try {
                        val response = hhApiService.searchVacancies(dto.toMap())
                        response.apply { resultCode = 200 }
                    } catch (e: Exception) {
                        val errorCode = when {
                            e.message?.contains("400") == true -> 400 // Bad Request
                            e.message?.contains("403") == true -> 403 // Captcha
                            e.message?.contains("404") == true -> 404 // Not Found
                            else -> 500 // Internal error
                        }
                        Response().apply { resultCode = errorCode }
                    }
                }

                is VacancyDetailsRequest -> {
                    try {
                        val response = hhApiService.getVacancy(dto.id)
                        response.apply { resultCode = 200 }
                    } catch (e: Exception) {
                        val errorCode = when {
                            e.message?.contains("403") == true -> 403 // Captcha
                            e.message?.contains("404") == true -> 404 // Not Found
                            e.message?.contains("429") == true -> 429 // Too Many Requests
                            else -> 500 // Internal error
                        }

                        Response().apply { resultCode = errorCode }
                    }
                }

                is IndustriesRequest -> {
                    try {
                        val response = hhApiService.getIndustries()
                        response.apply { resultCode = 200 }
                    } catch (e: Exception) {
                        Response().apply { resultCode = 500 }
                    }
                }

                is AreasRequest -> {
                    try {
                        val response = hhApiService.getAreas()
                        response.apply { resultCode = 200 }
                    } catch (e: Exception) {
                        Response().apply { resultCode = 500 }
                    }
                }

                else -> {
                    Response().apply { resultCode = 400 } // Bad Request
                }
            }
        }
    }
}
