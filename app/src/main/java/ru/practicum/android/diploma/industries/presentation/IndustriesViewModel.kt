package ru.practicum.android.diploma.industries.presentation

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.common.presentation.BaseSearchViewModel
import ru.practicum.android.diploma.common.presentation.FiltersUiState
import ru.practicum.android.diploma.common.presentation.ListUiState
import ru.practicum.android.diploma.industries.domain.IndustriesInteractor
import ru.practicum.android.diploma.industries.domain.models.Industry

class IndustriesViewModel(private val industriesInteractor: IndustriesInteractor) : BaseSearchViewModel<Industry>() {

    private var _currentIndustry: Industry? = null
    private val currentIndustry get() = _currentIndustry!!

    private var _loadedToLocalBaseFlag: Boolean? = null
    private val loadedToLocalBaseFlag get() = _loadedToLocalBaseFlag!!

    private var currentList: List<Industry> = mutableListOf()

    private var pendingQuery: String? = null

    private val fullIndustryListGetter: () -> Unit = {
        getFullIndustryList()
    }

    init {
        screenStateLiveData.value = ListUiState.Loading
        getFilterIndustry()
    }

    override fun onClickDebounce(item: Industry) {
        screenStateLiveData.postValue(FiltersUiState.FilterItem(item))
    }

    override suspend fun runSearch(currentQuery: String) {
        if (!loadedToLocalBaseFlag) {
            pendingQuery = currentQuery
            loadIndustries()
            return
        }

        industriesInteractor.getSearchList(currentQuery).collect { respons ->
            when {
                respons.first == BAD_REQUEST_CODE -> screenStateLiveData.postValue(ListUiState.ServerError)
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
        _currentIndustry = null
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
                    respons.first == BAD_REQUEST_CODE -> {
                        _loadedToLocalBaseFlag = false
                        screenStateLiveData.postValue(ListUiState.ServerError)
                    }

                    respons.first != SUCCESS_CODE -> {
                        _loadedToLocalBaseFlag = false
                        screenStateLiveData.postValue(ListUiState.Error)

                    }
                    respons.second.isNotEmpty() -> {
                        _loadedToLocalBaseFlag = true
                        currentList = respons.second

                        screenStateLiveData.postValue(ListUiState.Content(respons.second))
                        _currentIndustry?.let {
                            _currentIndustry = null
                            delay(UI_UPDATE_DELAY_MS)
                            showSelectItem(it)
                        }

                        pendingQuery?.let { query ->
                            pendingQuery = null
                            runSearch(query)
                        }
                    }

                    else -> {
                        _loadedToLocalBaseFlag = true
                        screenStateLiveData.postValue(ListUiState.Empty)
                    }
                }
            }
        }
    }

    private fun getFullIndustryList() {
        _currentIndustry = null
        if (!loadedToLocalBaseFlag) {
            screenStateLiveData.postValue(ListUiState.Error)
        } else {
            viewModelScope.launch {
                industriesInteractor.getLocalIndustryList().collect { respons ->
                    when {
                        respons.first == BAD_REQUEST_CODE -> screenStateLiveData.postValue(ListUiState.ServerError)
                        respons.second.isNotEmpty() -> {
                            currentList = respons.second

                            screenStateLiveData.postValue(ListUiState.Content(respons.second))
                        }

                        else -> screenStateLiveData.postValue(ListUiState.Empty)
                    }
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
        _loadedToLocalBaseFlag = null
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

    private fun getFilterIndustry() {
        viewModelScope.launch {
            industriesInteractor.getFilterIndustry()
                .collect { respons ->
                    _currentIndustry = respons
                    loadIndustries()
                }

        }
    }

    companion object {
        private const val UI_UPDATE_DELAY_MS = 50L
        private const val SUCCESS_CODE = 200
        private const val BAD_REQUEST_CODE = 400
        private const val INTERNAL_ERROR_CODE = 500
    }
}
