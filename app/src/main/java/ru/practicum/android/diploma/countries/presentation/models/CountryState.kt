package ru.practicum.android.diploma.countries.presentation.models

import ru.practicum.android.diploma.common.domain.models.Country

sealed class CountryState {
    object Loading : CountryState()
    object Error : CountryState()
    class Content(val countries: List<Country>) : CountryState()
}
