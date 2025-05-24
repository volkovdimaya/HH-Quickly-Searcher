package ru.practicum.android.diploma.industries.presentation

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.common.presentation.BaseSearchViewModel
import ru.practicum.android.diploma.common.presentation.FiltersUiState
import ru.practicum.android.diploma.common.presentation.ListUiState
import ru.practicum.android.diploma.industries.domain.IndustriesInteractor
import ru.practicum.android.diploma.industries.domain.models.Industry

class IndustriesViewModel(private val industriesInteractor: IndustriesInteractor) : BaseSearchViewModel<Industry>() {

    private val fullIndustryListGetter: () -> Unit = {
        getFullIndustryList()
    }

    init {
        loadIndustries()
    }

    override fun onClickDebounce(item: Industry) {
        screenStateLiveData.postValue(FiltersUiState.FilterItem(item))
    }

    override suspend fun runSearch(currentQuery: String) {
        industriesInteractor.getSearchList(currentQuery).collect { respons ->
            when {
                respons.first != SUCCESS_CODE -> screenStateLiveData.postValue(ListUiState.Error)
                respons.second.isNotEmpty() -> screenStateLiveData.postValue(ListUiState.Content(respons.second))
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
                    respons.first != SUCCESS_CODE -> screenStateLiveData.postValue(ListUiState.Error)
                    respons.second.isNotEmpty() -> screenStateLiveData.postValue(ListUiState.Content(respons.second))
                    else -> screenStateLiveData.postValue(ListUiState.Empty)
                }

            }
        }
    }

    private fun getFullIndustryList() {
        viewModelScope.launch {
            industriesInteractor.getLocalIndustryList().collect { respons ->
                when {
                    respons.second.isNotEmpty() -> screenStateLiveData.postValue(ListUiState.Content(respons.second))
                    else -> screenStateLiveData.postValue(ListUiState.Empty)
                }
            }
        }
    }

    companion object {
        private const val SUCCESS_CODE = 200
    }

}
