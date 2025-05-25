package ru.practicum.android.diploma.countries.data.impl

import android.app.Application
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.common.domain.models.Country
import ru.practicum.android.diploma.countries.domain.api.CountryRepository
import ru.practicum.android.diploma.countries.domain.models.CountryResponse
import ru.practicum.android.diploma.regions.data.dto.AreasRequest
import ru.practicum.android.diploma.regions.data.dto.AreasResponse
import ru.practicum.android.diploma.search.data.network.NetworkClient
import ru.practicum.android.diploma.util.isConnectedInternet

class CountryRepositoryImpl(
    private val networkClient: NetworkClient,
    private val application: Application
) : CountryRepository {
    override suspend fun getCountries(): Flow<CountryResponse> = flow {
        if (isConnectedInternet(application)) {
            emit(getCountriesFromNetwork())
        } else {
            emit(CountryResponse(BAD_REQUEST_CODE))
        }
    }

    private suspend fun getCountriesFromNetwork(): CountryResponse {
        val networkResponse = networkClient.doRequest(AreasRequest())
        val response = CountryResponse(networkResponse.resultCode)
        if (networkResponse is AreasResponse) {
            response.countries = networkResponse.areas.filter { area ->
                area.parentId == null
            }.map { area ->
                Country(
                    countryId = area.id,
                    countryName = area.name
                )
            }
        }

        return response
    }

    companion object {
        private const val BAD_REQUEST_CODE = 400
    }
}
