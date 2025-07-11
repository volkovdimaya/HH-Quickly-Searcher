package ru.practicum.android.diploma.countries.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.common.domain.models.Country

interface CountryRepositoryNetwork {
    suspend fun getCountries(): Flow<Pair<Int, List<Country>>>
}
