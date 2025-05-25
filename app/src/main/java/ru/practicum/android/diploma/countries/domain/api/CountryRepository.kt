package ru.practicum.android.diploma.countries.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.countries.domain.models.CountryResponse

interface CountryRepository {
    suspend fun getCountries(): Flow<CountryResponse>
}
