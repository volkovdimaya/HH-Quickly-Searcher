package ru.practicum.android.diploma.countries.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.countries.presentation.api.CountryInteractor
import ru.practicum.android.diploma.countries.presentation.models.CountryState

class CountryViewModel(val interactor: CountryInteractor) : ViewModel() {

    private var _state = MutableLiveData<CountryState>(CountryState.Loading)
    val state: LiveData<CountryState>
        get() = _state



    fun getCountries() {
        viewModelScope.launch {
            interactor.getCountries().collect { countryResponse ->
                val code = countryResponse.resultCode
                if (code == BAD_REQUEST_CODE) {
                    //TODO: Handle bad request error
                } else {
                }
            }
        }

    }

    companion object {
        private const val BAD_REQUEST_CODE = 400
    }
}
