package ru.practicum.android.diploma.search.presentation

import ru.practicum.android.diploma.common.presentation.ListUiState

interface SearchWithPagingUiState<out T> : ListUiState.ListUiIncludeState<T> {

    data class ContentWithMetadata<T>(
        val contentList: List<T>,
        val totalFound: Int,
        val pages: Int,
        val currentPage: Int
    ) : SearchWithPagingUiState<T>

    data class ContentWithMetadataRestate<T>(
        val state : ContentWithMetadata<T>,
        val pos : Int?
    ) : SearchWithPagingUiState<T>

    data class LoadingMoreError<T>(val currentList: List<T>) : SearchWithPagingUiState<T>

    data class LoadingMore<T>(val currentList: List<T>) : SearchWithPagingUiState<T>

    data class NewItems<T>(
        val newItems: List<T>,
        val totalFound: Int
    ) : SearchWithPagingUiState<T>

    interface SearchWithPagingUiIncludeState<out T> : SearchWithPagingUiState<T>
}


