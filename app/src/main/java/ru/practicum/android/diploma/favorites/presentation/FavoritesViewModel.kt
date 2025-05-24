package ru.practicum.android.diploma.favorites.presentation

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.common.domain.models.VacancyShort
import ru.practicum.android.diploma.common.presentation.ListUiState
import ru.practicum.android.diploma.favorites.data.local.DbClient.Companion.INTERNAL_ERROR_CODE
import ru.practicum.android.diploma.favorites.domain.interactors.FavoritesInteractor
import ru.practicum.android.diploma.util.debounce

class FavoritesViewModel(private val favoritesInteractor: FavoritesInteractor) : ViewModel() {

    private var screenStateLiveData = MutableLiveData<ListUiState<VacancyShort>>()
    private var previousScreenStateLiveData = MutableLiveData<ListUiState<VacancyShort>>()

    init {
        screenStateLiveData.value = ListUiState.Loading
        loadFavoritesList()
    }

    val observeState = MediatorLiveData<ListUiState<VacancyShort>>().apply {
        addSource(screenStateLiveData) { newValue ->
            previousScreenStateLiveData.value = this.value
            this.value = newValue
        }
    }

    fun restoreState() {
        screenStateLiveData.postValue(previousScreenStateLiveData.value)
    }

    private fun loadFavoritesList() {
        viewModelScope.launch {
            favoritesInteractor.loadFavorites()
                .collectLatest { pair ->
                    val screenState = when {
                        pair.first == INTERNAL_ERROR_CODE -> ListUiState.Error
                        pair.second.isNotEmpty() -> ListUiState.Content(pair.second)
                        else -> ListUiState.Empty
                    }
                    screenStateLiveData.postValue(screenState)
                }
        }
    }

    private val debouncedClick = debounce<VacancyShort>(
        delayMillis = 2000L,
        coroutineScope = viewModelScope,
        useLastParam = true
    ) { item ->
        onClickDebounce(item)
    }

    fun showVacancyDetails(item: VacancyShort) {
        debouncedClick(item)
    }

    private fun onClickDebounce(item: VacancyShort) {
        screenStateLiveData.postValue(ListUiState.AnyItem(item.vacancyId))
    }
}
