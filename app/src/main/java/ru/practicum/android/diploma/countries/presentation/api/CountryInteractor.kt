package ru.practicum.android.diploma.countries.presentation.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.common.domain.models.Country

interface CountryInteractor {
    suspend fun getCountries(): Flow<Pair<Int, List<Country>>>
    suspend fun saveCountry(country: Country)
}
