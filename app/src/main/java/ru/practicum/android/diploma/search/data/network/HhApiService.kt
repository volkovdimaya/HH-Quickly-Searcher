package ru.practicum.android.diploma.search.data.network

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.QueryMap
import ru.practicum.android.diploma.industries.data.dto.IndustryCategoryDto
import ru.practicum.android.diploma.regions.data.dto.AreaDto
import ru.practicum.android.diploma.search.data.dto.VacanciesResponse
import ru.practicum.android.diploma.search.data.dto.VacancyDetailsResponse

interface HhApiService {

    @Headers(
//        "Authorization: Bearer ${BuildConfig.HH_ACCESS_TOKEN}",
        "HH-User-Agent: HH Quickly Searcher (rogalevas@live.com)"
    )
    @GET("/vacancies")
    suspend fun searchVacancies(@QueryMap filters: Map<String, String>): VacanciesResponse

    @Headers(
//        "Authorization: Bearer ${BuildConfig.HH_ACCESS_TOKEN}",
        "HH-User-Agent: HH Quickly Searcher (rogalevas@live.com)"
    )
    @GET("/vacancies/{vacancy_id}")
    suspend fun getVacancy(@Path("vacancy_id") id: String): VacancyDetailsResponse

    @GET("/industries")
    suspend fun getIndustries(): List<IndustryCategoryDto>

    @GET("/areas")
    suspend fun getAreas(): List<AreaDto>

    @GET("/areas/{area_id}")
    suspend fun getAreaById(@Path("area_id") areaId: String): AreaDto
}
