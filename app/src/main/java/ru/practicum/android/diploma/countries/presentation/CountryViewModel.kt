package ru.practicum.android.diploma.countries.presentation

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.common.domain.models.Country
import ru.practicum.android.diploma.common.presentation.BaseSearchViewModel
import ru.practicum.android.diploma.common.presentation.FiltersUiState
import ru.practicum.android.diploma.common.presentation.ListUiState
import ru.practicum.android.diploma.countries.presentation.api.CountryInteractor

class CountryViewModel(val interactor: CountryInteractor) : BaseSearchViewModel<Country>() {

    init {
        screenStateLiveData.value = ListUiState.Loading
        getCountries()
    }

    private fun getCountries() {
        viewModelScope.launch {
            interactor.getCountries().collect { countryResponse ->
                val code = countryResponse.first
                when {
                    code == INTERNAL_ERROR_CODE -> screenStateLiveData.postValue(ListUiState.Error)
                    code != SUCCESS_CODE ->
                        screenStateLiveData.postValue(ListUiState.ServerError)
                    countryResponse.second.isNotEmpty() -> {
                        val sortedList = countryResponse.second.sortedBy {
                            it.countryName == "Другие регионы"
                        }
                        screenStateLiveData.postValue(ListUiState.Content(sortedList))
                    }

                    else -> screenStateLiveData.postValue(ListUiState.Empty)
                }
            }
        }

    }

    companion object {
        private const val SUCCESS_CODE = 200
        private const val BAD_REQUEST_CODE = 400
        private const val INTERNAL_ERROR_CODE = 500
    }

    override suspend fun runSearch(currentQuery: String) {
        TODO()
    }

    override fun onClickDebounce(item: Country) {
        screenStateLiveData.postValue(FiltersUiState.FilterItem(item))
    }

    fun showSelectItem(it: Country) {
        viewModelScope.launch(Dispatchers.IO) {
            interactor.saveCountry(it)
            screenStateLiveData.postValue(FiltersUiState.SuccessAddDb)
        }
    }
}
