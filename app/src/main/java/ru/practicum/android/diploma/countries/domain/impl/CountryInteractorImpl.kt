package ru.practicum.android.diploma.countries.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.common.domain.models.Country
import ru.practicum.android.diploma.countries.domain.api.CountryRepositoryNetwork
import ru.practicum.android.diploma.countries.presentation.api.CountryInteractor
import ru.practicum.android.diploma.filters.domain.api.FilterParametersRepository
import ru.practicum.android.diploma.filters.domain.models.FilterParametersType

class CountryInteractorImpl(
    private val repNetwork: CountryRepositoryNetwork,
    private val repBd: FilterParametersRepository
) : CountryInteractor {

    override suspend fun getCountries(): Flow<Pair<Int, List<Country>>> {
        return repNetwork.getCountries()
    }

    override suspend fun saveCountry(country: Country) {
        repBd.saveFilterParameters(
            FilterParametersType.Country(countryId = country.countryId, countryName = country.countryName)
        )
    }

}
