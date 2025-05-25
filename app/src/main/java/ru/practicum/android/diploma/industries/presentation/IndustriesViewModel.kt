package ru.practicum.android.diploma.industries.presentation

import android.util.Log
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
        screenStateLiveData.value = ListUiState.Default
        loadIndustries()
    }

    override fun onClickDebounce(item: Industry) {
        screenStateLiveData.postValue(FiltersUiState.FilterItem(item))
    }

    override suspend fun runSearch(currentQuery: String) {
        industriesInteractor.getSearchList(currentQuery).collect { respons ->
            when {
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
                    respons.first != SUCCESS_CODE -> screenStateLiveData.postValue(ListUiState.Error)
                    respons.second.isNotEmpty() -> {
                        currentList = respons.second
                        screenStateLiveData.postValue(ListUiState.Content(respons.second))

                    }
                    else -> screenStateLiveData.postValue(ListUiState.Empty)
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

    fun showSelectButton(item: Industry) {
        Log.d("industr", "Click 2")
        currentList.forEach {
            if (it.industryId == item.industryId) {
                Log.d("industr", "if (it.industryId == item.industryId)")
                it.apply { select.isSelected = true }
            } else {
                it.apply { select.isSelected = false }
            }

        }
        Log.d("industr", "Click 3")
        Log.d("industr", "currentList 1")
     //Log.d("industr", "Click 2")  currentList.forEach { Log. d("industr", "currentList ${it.select.isSelected} ${it}") }
        _currentIndustry = item
        screenStateLiveData.postValue(FiltersUiState.SelectPosition(currentList))
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

    companion object {
        private const val SUCCESS_CODE = 200
    }
}
