package ru.practicum.android.diploma.countries.domain.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.practicum.android.diploma.common.domain.models.Country
import ru.practicum.android.diploma.countries.domain.api.CountryRepositoryNetwork
import ru.practicum.android.diploma.countries.presentation.api.CountryInteractor
import ru.practicum.android.diploma.filters.domain.api.FilterParametersRepository
import ru.practicum.android.diploma.filters.domain.models.FilterParametersType

class CountryInteractorImpl(
    private val repNetwork: CountryRepositoryNetwork,
    private val repBd: FilterParametersRepository
) : CountryInteractor {

    companion object {
        private val SHORT_COUNTRY_LIST = listOf(
            "Россия",
            "Украина",
            "Казахстан",
            "Азербайджан",
            "Беларусь",
            "Грузия",
            "Кыргызстан",
            "Узбекистан",
        )
    }

    override suspend fun getCountries(): Flow<Pair<Int, List<Country>>> {
        return repNetwork.getCountries().map { pair ->
            val code = pair.first
            val countries = pair.second
            val filtered = countries.filter { it.countryName in SHORT_COUNTRY_LIST }
            val manualCountry = Country(0, "Другие регионы")
            code to filtered + manualCountry
        }
    }

    override suspend fun saveCountry(country: Country) {
        repBd.saveFilterParameters(
            FilterParametersType.Country(countryId = country.countryId, countryName = country.countryName)
        )
    }

}
