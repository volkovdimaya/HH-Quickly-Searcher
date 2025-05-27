package ru.practicum.android.diploma.countries.data.impl

import android.app.Application
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.common.domain.models.Country
import ru.practicum.android.diploma.countries.data.dto.CountryResponse
import ru.practicum.android.diploma.countries.domain.api.CountryRepositoryNetwork
import ru.practicum.android.diploma.regions.data.dto.AreasRequest
import ru.practicum.android.diploma.search.data.network.NetworkClient
import ru.practicum.android.diploma.util.isConnectedInternet

class CountryRepositoryNetworkImpl(
    private val networkClient: NetworkClient,
    private val application: Application
) : CountryRepositoryNetwork {
    override suspend fun getCountries(): Flow<Pair<Int, List<Country>>> = flow {
        if (isConnectedInternet(application)) {
            emit(getCountriesFromNetwork())
        } else {
            emit(Pair(BAD_REQUEST_CODE, listOf()))
        }
    }

    private suspend fun getCountriesFromNetwork(): Pair<Int, List<Country>> {
        val networkResponse = networkClient.doRequest(AreasRequest())

        return if (networkResponse is CountryResponse) {
            Pair(networkResponse.resultCode, mapper(networkResponse))
        } else {
            Pair(networkResponse.resultCode, listOf())
        }
    }

    private fun mapper(countryResponse: CountryResponse): List<Country> {
        return countryResponse.countries.filter { area ->
            area.parentId == null
        }.map { area ->
            Country(
                countryId = area.id,
                countryName = area.name
            )
        }
    }

    companion object {
        private const val BAD_REQUEST_CODE = 400
    }
}
