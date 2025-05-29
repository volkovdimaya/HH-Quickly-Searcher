package ru.practicum.android.diploma.common.presentation

import ru.practicum.android.diploma.common.domain.models.VacancyShort

interface ShortVacancyListUiState : ListUiState<VacancyShort> {

    data object Loading : ShortVacancyListUiState

    data object Empty : ShortVacancyListUiState

    data object Default : ShortVacancyListUiState

    data object Error : ShortVacancyListUiState

    data class Content(val contentList: List<VacancyShort>) : ShortVacancyListUiState

    data class AnyItem(val itemId: String) : ShortVacancyListUiState

    data class LoadingMore(val currentList: List<VacancyShort>) : ShortVacancyListUiIncludeState

    data class LoadingMoreError(val currentList: List<VacancyShort>) : ShortVacancyListUiIncludeState

    data class ContentWithMetadata(
        val contentList: List<VacancyShort>,
        val totalFound: Int,
        val pages: Int,
        val currentPage: Int
    ) : ShortVacancyListUiState

    data class NewItems(
        val newItems: List<VacancyShort>,
        val totalFound: Int
    ) : ShortVacancyListUiState

    data object ServerError : ShortVacancyListUiState

    interface ShortVacancyListUiIncludeState : ListUiState.ListUiIncludeState<VacancyShort>,
        ShortVacancyListUiState
}
