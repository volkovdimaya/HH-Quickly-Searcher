package ru.practicum.android.diploma.common.ui.fragments

import androidx.viewbinding.ViewBinding
import ru.practicum.android.diploma.common.presentation.ListUiState
import ru.practicum.android.diploma.databinding.LayoutNoInternetBinding
import ru.practicum.android.diploma.databinding.LayoutServerErrorBinding

abstract class ListWithInternetFragment<T, V : ViewBinding> : BaseListFragment<T, V>() {

    private var _noInternetErrorBinding: LayoutNoInternetBinding? = null
    val noInternetErrorBinding get() = _noInternetErrorBinding!!

    private var _serverErrorBinding: LayoutServerErrorBinding? = null
    val serverErrorBinding get() = _serverErrorBinding!!

    override fun onDestroyView() {
        super.onDestroyView()
        _noInternetErrorBinding = null
        _serverErrorBinding = null
    }

    override fun initViews() {
        super.initViews()
        _noInternetErrorBinding = LayoutNoInternetBinding.inflate(layoutInflater)
        _serverErrorBinding = LayoutServerErrorBinding.inflate(layoutInflater)
    }

    override fun updateIncludeViewByError() {
        updateIncludeView(noInternetErrorBinding.root)
    }

    open fun updateIncludeViewByServerError() {
        updateIncludeView(serverErrorBinding.root)
    }

    override fun render(state: ListUiState<T>) {
        when (state) {
            is ListUiState.AnyItem -> goToFragment(state.itemId)
            is ListUiState.Content<T> -> updateIncludeViewByList(state.contentList)
            ListUiState.Default -> updateIncludeViewByClear()
            ListUiState.Empty -> updateIncludeViewByEmpty()
            ListUiState.Loading -> updateIncludeViewByProgressBar()
            ListUiState.Error -> updateIncludeViewByError()
            ListUiState.ServerError -> updateIncludeViewByServerError()
            is ListUiState.ListUiIncludeState -> renderIncludeState(state)
        }
    }
}
