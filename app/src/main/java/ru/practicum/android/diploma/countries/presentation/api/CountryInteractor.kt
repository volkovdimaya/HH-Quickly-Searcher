package ru.practicum.android.diploma.countries.presentation.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.countries.domain.models.CountryResponseDomain

interface CountryInteractor {
    suspend fun getCountries(): Flow<CountryResponseDomain>
}
