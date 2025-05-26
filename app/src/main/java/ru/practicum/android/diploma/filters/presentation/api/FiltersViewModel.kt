package ru.practicum.android.diploma.filters.presentation.api

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.filters.domain.api.FilterParametersInteractor
import ru.practicum.android.diploma.filters.domain.models.FilterParameters
import ru.practicum.android.diploma.filters.domain.models.FilterParametersType

class FiltersViewModel(
    private val filterInteractor: FilterParametersInteractor
) : ViewModel() {

    private val filterParametersState = MutableLiveData(FilterParameters())
    fun getFilterParametersState(): LiveData<FilterParameters> = filterParametersState

    init {
        updateFilters()
    }

    fun deleteFilterParameter(parameter: FilterParametersType) {
        addFilterParameter(parameter)
    }

    fun addFilterParameter(parameter: FilterParametersType) {
        viewModelScope.launch {
            filterInteractor.updateFilterParameter(parameter)
        }
    }

    fun updateFilters() {
        viewModelScope.launch {
            filterInteractor.getFilterParameters().collect {
                filterParametersState.postValue(it)
            }
        }
    }
}
