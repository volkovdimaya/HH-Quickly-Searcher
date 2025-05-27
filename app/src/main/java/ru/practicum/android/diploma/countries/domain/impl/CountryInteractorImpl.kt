package ru.practicum.android.diploma.countries.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.common.domain.models.Country
import ru.practicum.android.diploma.countries.domain.api.CountryRepositoryNetwork
import ru.practicum.android.diploma.countries.presentation.api.CountryInteractor

class CountryInteractorImpl(val rep : CountryRepositoryNetwork) : CountryInteractor {
    override suspend fun getCountries(): Flow<Pair<Int, List<Country>>> {
       return rep.getCountries()
    }

    override suspend fun saveCountry(country: Country): Flow<Int> {
        TODO("Not yet implemented")
    }


}
