package ru.practicum.android.diploma.countries.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.countries.domain.api.CountryRepository
import ru.practicum.android.diploma.countries.domain.models.CountryResponseDomain
import ru.practicum.android.diploma.countries.presentation.api.CountryInteractor

class CountryInteractorImpl(val rep : CountryRepository) : CountryInteractor {
    override suspend fun getCountries(): Flow<CountryResponseDomain> {
       return rep.getCountries()
    }


}
