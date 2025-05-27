package ru.practicum.android.diploma.countries.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.common.domain.models.Country
import ru.practicum.android.diploma.common.presentation.BaseSearchViewModel
import ru.practicum.android.diploma.common.presentation.FiltersUiState
import ru.practicum.android.diploma.common.presentation.ListUiState
import ru.practicum.android.diploma.countries.presentation.api.CountryInteractor
import ru.practicum.android.diploma.countries.presentation.models.CountryState
import ru.practicum.android.diploma.industries.domain.models.Industry

class CountryViewModel(val interactor: CountryInteractor) : BaseSearchViewModel<Country>() {


    init {
        screenStateLiveData.value = ListUiState.Loading
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
                    screenStateLiveData.postValue(ListUiState.Content(countries))
                }
            }
        }

    }

    companion object {
        private const val BAD_REQUEST_CODE = 400
    }

    override suspend fun runSearch(currentQuery: String) {
        TODO("Not yet implemented")
    }

    override fun onClickDebounce(item: Country) {
        TODO("Not yet implemented")
    }

    fun showSelectItem(it: Country) {
        viewModelScope.launch {
            // TODO: добавить в базу данных  
            screenStateLiveData.postValue(FiltersUiState.SuccessAddDb)
        }
        
    }

    fun saveFilterParameter(item: Country) {

    }
}
