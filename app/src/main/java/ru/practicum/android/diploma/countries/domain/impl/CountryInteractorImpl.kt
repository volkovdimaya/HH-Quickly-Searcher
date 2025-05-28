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

    override suspend fun getCountries(): Flow<Pair<Int, List<Country>>> {
        return repNetwork.getCountries().map { pair ->
            val code = pair.first
            val countries = pair.second
            val filtered = countries.filter { it.countryName in getShortCountryList() }
                .sortedBy { it.countryName }
            val manualCountry = Country(0, "Другие регионы")
            code to filtered + manualCountry
        }
    }

    private fun getShortCountryList(): List<String> {
        return listOf(
            "Россия",
            "Украина",
            "Казахстан",
            "Азербайджан",
            "Беларусь",
            "Грузия",
            "Кыргыстан",
            "Узбекистан",
        )
    }

    override suspend fun saveCountry(country: Country) {
        repBd.saveFilterParameters(
            FilterParametersType.Country()
        )
    }

}
