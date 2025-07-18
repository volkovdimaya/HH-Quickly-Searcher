package ru.practicum.android.diploma.regions.presentation

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.common.presentation.BaseSearchViewModel
import ru.practicum.android.diploma.common.presentation.FiltersUiState
import ru.practicum.android.diploma.common.presentation.ListUiState
import ru.practicum.android.diploma.regions.domain.RegionsInteractor
import ru.practicum.android.diploma.regions.domain.models.Region

class RegionsViewModel(
    private val regionsInteractor: RegionsInteractor,
) : BaseSearchViewModel<Region>() {

    private var currentCountryId: Int? = null

    private var loadedToLocalBaseFlag: Boolean = false

    private var pendingQuery: String? = null

    private val fullRegionListGetter: () -> Unit = {
        getFullRegionList()
    }

    init {
        screenStateLiveData.value = ListUiState.Loading
        loadRegions()
    }

    override fun onClickDebounce(item: Region) {
        screenStateLiveData.postValue(FiltersUiState.FilterItem(item))
    }

    override suspend fun runSearch(currentQuery: String) {
        if (!loadedToLocalBaseFlag) {
            pendingQuery = currentQuery
            loadRegions()
            return
        }

        regionsInteractor.getSearchList(currentQuery).collect { response ->
            when {
                response.first == INTERNAL_ERROR_CODE -> {
                    loadedToLocalBaseFlag = false
                    screenStateLiveData.postValue(ListUiState.Error)
                }
                response.first != SUCCESS_CODE -> {
                    screenStateLiveData.postValue(ListUiState.ServerError)
                }
                response.second.isNotEmpty() -> {
                    screenStateLiveData.postValue(ListUiState.Content(response.second))
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
                fullRegionListGetter()
            }
        } else if (textChanged) {
            searchDebounceJob?.cancel()
            searchDebounceJob = viewModelScope.launch {
                searchDebouncer(text)
            }
        }
    }

    private fun loadRegions() {
        viewModelScope.launch {
            currentCountryId = regionsInteractor.getCurrentCountryId()
            regionsInteractor.loadRegions(currentCountryId?.toString()).collect { response ->
                when {
                    response.first == BAD_REQUEST_CODE -> {
                        loadedToLocalBaseFlag = false
                        screenStateLiveData.postValue(ListUiState.ServerError)
                    }
                    response.first != SUCCESS_CODE -> {
                        loadedToLocalBaseFlag = false
                        screenStateLiveData.postValue(ListUiState.Error)
                    }
                    response.second.isNotEmpty() -> {
                        loadedToLocalBaseFlag = true
                        pendingQuery?.let { query ->
                            pendingQuery = null
                            runSearch(query)
                        } ?: screenStateLiveData.postValue(ListUiState.Content(response.second))
                    }
                    else -> {
                        loadedToLocalBaseFlag = true
                        screenStateLiveData.postValue(ListUiState.Empty)
                    }
                }
            }
        }
    }

    private fun getFullRegionList() {
        if (!loadedToLocalBaseFlag) {
            screenStateLiveData.postValue(ListUiState.Error)
        } else {
            viewModelScope.launch {
                regionsInteractor.getLocalRegionsList().collect { response ->
                    when {
                        response.second.isNotEmpty() -> {
                            screenStateLiveData.postValue(ListUiState.Content(response.second))
                        }

                        else -> screenStateLiveData.postValue(ListUiState.Empty)
                    }
                }
            }
        }
    }

    private fun clearTableDb() {
        viewModelScope.launch {
            regionsInteractor.clearTableDb()
        }
    }

    override fun onCleared() {
        super.onCleared()
        clearTableDb()
    }

    fun saveFilterParameter(item: Region) {
        viewModelScope.launch {
            regionsInteractor.saveFilterParameter(item).collect { code ->
                if (code == SUCCESS_CODE) {
                    screenStateLiveData.postValue(FiltersUiState.FilterItem(item))
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
