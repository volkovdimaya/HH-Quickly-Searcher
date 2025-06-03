package ru.practicum.android.diploma.industries.presentation

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.common.presentation.BaseSearchViewModel
import ru.practicum.android.diploma.common.presentation.FiltersUiState
import ru.practicum.android.diploma.common.presentation.ListUiState
import ru.practicum.android.diploma.industries.domain.IndustriesInteractor
import ru.practicum.android.diploma.industries.domain.models.Industry

class IndustriesViewModel(private val industriesInteractor: IndustriesInteractor) : BaseSearchViewModel<Industry>() {

    private var _currentIndustry: Industry? = null
    private val currentIndustry get() = _currentIndustry!!

    private var currentList: List<Industry> = mutableListOf()

    private val fullIndustryListGetter: () -> Unit = {
        getFullIndustryList()
    }

    init {
        screenStateLiveData.value = ListUiState.Loading
        loadIndustries()
    }

    override fun onClickDebounce(item: Industry) {
        screenStateLiveData.postValue(FiltersUiState.FilterItem(item))
    }

    override suspend fun runSearch(currentQuery: String) {
        industriesInteractor.getSearchList(currentQuery).collect { respons ->
            when {
                respons.first == BAD_REQUEST_CODE -> screenStateLiveData.postValue(ListUiState.ServerError)
                respons.first != SUCCESS_CODE -> screenStateLiveData.postValue(ListUiState.Error)
                respons.second.isNotEmpty() -> {
                    currentList = respons.second
                    screenStateLiveData.postValue(ListUiState.Content(respons.second))
                }
                else -> screenStateLiveData.postValue(ListUiState.Empty)
            }
        }
    }

    override fun onSearchTextChanged(text: String) {
        val textChanged = currentQuery != text
        currentQuery = text

        if (currentQuery.isEmpty()) {
            searchDebounceJob?.cancel()
            searchDebounceJob = viewModelScope.launch {
                fullIndustryListGetter()
            }
        } else if (textChanged) {
            searchDebounceJob?.cancel()
            searchDebounceJob = viewModelScope.launch {
                searchDebouncer(text)
            }
        }
    }

    private fun loadIndustries() {
        viewModelScope.launch {
            industriesInteractor.loadIndustries().collect { respons ->
                when {
                    respons.first == BAD_REQUEST_CODE -> screenStateLiveData.postValue(ListUiState.ServerError)
                    respons.first != SUCCESS_CODE -> screenStateLiveData.postValue(ListUiState.Error)
                    respons.second.isNotEmpty() -> {
                        currentList = respons.second
                        screenStateLiveData.postValue(ListUiState.Content(respons.second))

                    }
                    else -> screenStateLiveData.postValue(ListUiState.Empty)
                }

            }

            industriesInteractor.getFilterIndustry()
                .collect { respons ->
                    if (respons != null && screenStateLiveData.value is ListUiState.Content) {
                        _currentIndustry = respons
                        showSelectItem(currentIndustry)
                    }
                }
        }
    }

    private fun getFullIndustryList() {
        viewModelScope.launch {
            industriesInteractor.getLocalIndustryList().collect { respons ->
                when {
                    respons.second.isNotEmpty() -> {
                        currentList = respons.second
                        screenStateLiveData.postValue(ListUiState.Content(currentList))
                    }
                    else -> screenStateLiveData.postValue(ListUiState.Empty)
                }
            }
        }
    }

    fun showSelectItem(item: Industry) {
        val newList = currentList.map { industry ->
            val shouldBeSelected = industry.industryId == item.industryId
            if (industry.select != shouldBeSelected) {
                val copy = industry.copy(select = shouldBeSelected)
                _currentIndustry = copy
                copy
            } else if (industry.select) {
                industry.copy(select = false)
            } else {
                industry
            }
        }
        screenStateLiveData.postValue(FiltersUiState.SelectPosition(newList))
    }

    fun showAppropriateFragment() {
        screenStateLiveData.postValue(FiltersUiState.FilterItem(currentIndustry))
    }

    private fun clearTableDb() {
        viewModelScope.launch {
            industriesInteractor.clearTableDb()
        }
    }

    override fun onCleared() {
        super.onCleared()
        clearTableDb()
        _currentIndustry = null
    }

    fun saveFilterParameter(item: Industry) {
        viewModelScope.launch {
            industriesInteractor.saveFilterParameter(item).collect { code ->
                if (code == SUCCESS_CODE) {
                    screenStateLiveData.postValue(ListUiState.AnyItem(code.toString()))
                } else {
                    screenStateLiveData.postValue(FiltersUiState.NoChange)
                }
            }
        }
    }

    companion object {
        private const val SUCCESS_CODE = 200
        private const val BAD_REQUEST_CODE = 400
        private const val INTERNAL_ERROR_CODE = 500
    }
}
