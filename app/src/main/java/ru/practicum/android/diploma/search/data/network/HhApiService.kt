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
        "Authorization: Bearer GTMFA6J7BBJQM5TEJHI3I05DDI4SSGC60CTA73LP4JN8T9KF9N9DT16H9K8DRFB0",
        "HH-User-Agent: HH Quickly Searcher (rogalevas@live.com)"
    )
    @GET("/vacancies")
    suspend fun searchVacancies(@QueryMap filters: Map<String, String>): VacanciesResponse

    @Headers(
        "Authorization: Bearer GTMFA6J7BBJQM5TEJHI3I05DDI4SSGC60CTA73LP4JN8T9KF9N9DT16H9K8DRFB0",
        "HH-User-Agent: HH Quickly Searcher (rogalevas@live.com)"
    )
    @GET("/vacancies/{vacancy_id}")
    suspend fun getVacancy(@Path("vacancy_id") id: String): VacancyDetailsResponse

    @GET("/industries")
    suspend fun getIndustries(): IndustriesResponse

    @GET("/areas")
    suspend fun getAreas(): AreasResponse
}
