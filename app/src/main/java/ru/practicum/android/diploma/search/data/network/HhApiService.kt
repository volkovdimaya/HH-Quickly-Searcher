package ru.practicum.android.diploma.search.data.network

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.QueryMap
import ru.practicum.android.diploma.industries.data.dto.IndustriesResponse
import ru.practicum.android.diploma.regions.data.dto.AreasResponse
import ru.practicum.android.diploma.search.data.dto.VacanciesResponse
import ru.practicum.android.diploma.search.data.dto.VacancyDetailsResponse

interface HhApiService {

    @Headers(
        "Authorization: Bearer GH_HH_ACCESS_TOKEN",
        "HH-User-Agent: HH Quickly Searcher (rogalevas@live.com)"
    )
    @GET("/vacancies")
    suspend fun searchVacancies(@QueryMap filters: Map<String, String>): VacanciesResponse

    @Headers(
        "Authorization: Bearer GH_HH_ACCESS_TOKEN",
        "HH-User-Agent: HH Quickly Searcher (rogalevas@live.com)"
    )
    @GET("/vacancies/{vacancy_id}")
    suspend fun getVacancy(@Path("vacancy_id") id: String): VacancyDetailsResponse

    @GET("/industries")
    suspend fun getIndustries(): IndustriesResponse

    @GET("/areas")
    suspend fun getAreas(): AreasResponse
}
