package ru.practicum.android.diploma.common.ui.fragments

import androidx.viewbinding.ViewBinding
import ru.practicum.android.diploma.common.domain.models.VacancyShort
import ru.practicum.android.diploma.common.presentation.ListUiState
import ru.practicum.android.diploma.common.presentation.ShortVacancyListUiState
import ru.practicum.android.diploma.common.ui.adapters.ShortVacancyAdapter

abstract class ShortVacancyFragment<T : ViewBinding> : ListWithSearchFragment<VacancyShort, T>() {

    abstract override val adapter: ShortVacancyAdapter

    override fun render(state: ListUiState<VacancyShort>) {
        when (state) {
            is ShortVacancyListUiState.AnyItem -> goToFragment(state.itemId)
            is ShortVacancyListUiState.Content -> updateIncludeViewByList(state.contentList)
            ShortVacancyListUiState.Default -> updateIncludeViewByClear()
            ShortVacancyListUiState.Empty -> updateIncludeViewByEmpty()
            ShortVacancyListUiState.Loading -> updateIncludeViewByProgressBar()
            ShortVacancyListUiState.Error -> updateIncludeViewByError()
            is ShortVacancyListUiState.ShortVacancyListUiIncludeState -> renderIncludeState(state)
        }
    }

}
