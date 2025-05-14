package ru.practicum.android.diploma.search.data.network

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap
import ru.practicum.android.diploma.search.data.dto.VacanciesResponse
import ru.practicum.android.diploma.search.data.dto.VacancyDetailsResponse

interface HhApiService {

    @GET("/vacancies")
    suspend fun searchVacancies(@QueryMap filters: Map<String, String>): VacanciesResponse


    @GET("/vacancies/{id}")
    suspend fun getVacancy(@Path("id") id: String): VacancyDetailsResponse
}
