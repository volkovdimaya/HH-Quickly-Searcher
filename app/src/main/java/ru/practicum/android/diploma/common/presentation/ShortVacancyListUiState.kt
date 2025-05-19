package ru.practicum.android.diploma.common.presentation

import ru.practicum.android.diploma.common.domain.models.VacancyShort

interface ShortVacancyListUiState {

    data object Loading : ShortVacancyListUiState

    data object Empty : ShortVacancyListUiState

    data object Default : ShortVacancyListUiState

    data object Error : ShortVacancyListUiState

    data class Content(val contentList: List<VacancyShort>) : ShortVacancyListUiState

    data class AnyItem(val itemId: Int) : ShortVacancyListUiState

    interface ShortVacancyListUiIncludeState : ShortVacancyListUiState
}
