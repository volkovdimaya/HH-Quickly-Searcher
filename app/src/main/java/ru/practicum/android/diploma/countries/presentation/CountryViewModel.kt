package ru.practicum.android.diploma.countries.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.common.domain.models.Country
import ru.practicum.android.diploma.countries.presentation.api.CountryInteractor
import ru.practicum.android.diploma.countries.presentation.models.CountryState

class CountryViewModel(val interactor: CountryInteractor) : ViewModel() {

    private var _state = MutableLiveData<CountryState>(CountryState.Loading)
    val state: LiveData<CountryState>
        get() = _state


    init {
        getCountries()
    }

    fun getCountries() {
        viewModelScope.launch {
            interactor.getCountries().collect { countryResponse ->
                Log.d("CountryViewModel", "Received country response: $countryResponse")
                val code = countryResponse.first
                if (code == BAD_REQUEST_CODE) {
                    //TODO: Handle bad request error
                } else {
                    val countries = countryResponse.second as List<Country>
                    Log.d("CountryViewModel", "Countries: $countries")
                        _state.value = CountryState.Content(countries)
                }
            }
        }

    }

    companion object {
        private const val BAD_REQUEST_CODE = 400
    }
}
