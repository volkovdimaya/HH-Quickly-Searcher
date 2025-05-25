package ru.practicum.android.diploma.countries.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.countries.domain.models.CountryResponseDomain

interface CountryRepository {
    suspend fun getCountries(): Flow<CountryResponseDomain>
}
