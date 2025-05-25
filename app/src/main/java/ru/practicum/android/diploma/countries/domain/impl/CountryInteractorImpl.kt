package ru.practicum.android.diploma.countries.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.common.domain.models.Country
import ru.practicum.android.diploma.countries.domain.api.CountryRepository
import ru.practicum.android.diploma.countries.data.dto.CountryResponse
import ru.practicum.android.diploma.countries.presentation.api.CountryInteractor

class CountryInteractorImpl(val rep : CountryRepository) : CountryInteractor {
    override suspend fun getCountries(): Flow<Pair<Int, List<Country>>> {
       return rep.getCountries()
    }


}
