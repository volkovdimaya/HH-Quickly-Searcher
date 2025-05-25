package ru.practicum.android.diploma.countries.domain.models

import ru.practicum.android.diploma.common.domain.models.Country

class CountryResponseDomain(var resultCode: Int) {

    var countries: List<Country> = emptyList()
}
