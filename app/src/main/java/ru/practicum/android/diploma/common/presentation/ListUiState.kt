package ru.practicum.android.diploma.common.presentation

interface ListUiState<out T> {

    data object Loading : ListUiState<Nothing>

    data object Empty : ListUiState<Nothing>

    data object Default : ListUiState<Nothing>

    data object Error : ListUiState<Nothing>

    data class Content<T>(val contentList: List<T>) : ListUiState<T>

    data class AnyItem<T>(val itemId: String) : ListUiState<T>

    interface ListUiIncludeState<out T> : ListUiState<T>
}
