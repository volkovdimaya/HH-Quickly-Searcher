package ru.practicum.android.diploma.filters.presentation.api

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.filters.domain.api.FilterParametersInteractor
import ru.practicum.android.diploma.filters.domain.models.FilterParameters
import ru.practicum.android.diploma.filters.domain.models.FilterParametersType
import ru.practicum.android.diploma.util.debounce

class FiltersViewModel(
    private val filterInteractor: FilterParametersInteractor
) : ViewModel() {

    private val filterParametersState = MutableLiveData(FilterParameters())
    fun getFilterParametersState(): LiveData<FilterParameters> = filterParametersState

    private var previousParameters: FilterParameters? = null

    val saveDebouncer = debounce<FilterParametersType>(
        delayMillis = SAVE_DEBOUNCE_DELAY,
        coroutineScope = viewModelScope,
        useLastParam = true
    ) {
        addFilterParameter(it)
    }

    init {
        updateFilters()
        previousParameters = filterParametersState.value
    }

    fun addWithDebounce(parameter: FilterParametersType) {
        saveDebouncer(parameter)
    }

    fun deleteFilterParameter(parameter: FilterParametersType) {
        addFilterParameter(parameter)
    }

    fun deleteAllFilters() {
        viewModelScope.launch {
            filterInteractor.deleteAllFilters()
        }
        updateFilters()
    }

    fun addFilterParameter(parameter: FilterParametersType) {
        viewModelScope.launch {
            filterInteractor.updateFilterParameter(parameter)
        }
        updateFilters()
    }

    fun updateFilters() {
        viewModelScope.launch {
            filterInteractor.getFilterParameters().collect {
                filterParametersState.postValue(it)
            }
        }
    }

    companion object {
        private const val SAVE_DEBOUNCE_DELAY = 300L
    }
}
