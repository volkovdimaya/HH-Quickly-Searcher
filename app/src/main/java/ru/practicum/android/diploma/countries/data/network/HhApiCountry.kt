package ru.practicum.android.diploma.countries.data.network

import retrofit2.http.GET
import ru.practicum.android.diploma.countries.data.dto.AreaNetworkDto

interface HhApiCountry {
    @GET("areas")
    suspend fun getAreas(): List<AreaNetworkDto>
}
