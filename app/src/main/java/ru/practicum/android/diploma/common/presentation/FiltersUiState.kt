package ru.practicum.android.diploma.common.presentation

interface FiltersUiState<out T> : ListUiState.ListUiIncludeState<T> {

    data class FilterItem<T>(val item: T) : FiltersUiState<T>

    interface FiltersUiIncludeState<out T> : FiltersUiState<T>
}
