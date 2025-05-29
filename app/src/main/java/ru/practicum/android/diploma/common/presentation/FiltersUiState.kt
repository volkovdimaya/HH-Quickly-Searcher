package ru.practicum.android.diploma.common.presentation

interface FiltersUiState<out T> : ListUiState.ListUiIncludeState<T> {

    data object NoChange : FiltersUiState<Nothing>

    data object SuccessAddDb : FiltersUiState<Nothing>

    data class SelectPosition<T>(val newList: List<T>) : FiltersUiState<T>

    data class FilterItem<T>(val item: T) : FiltersUiState<T>

    interface FiltersUiIncludeState<out T> : FiltersUiState<T>
}
