package ru.practicum.android.diploma.common.ui.fragments

import androidx.viewbinding.ViewBinding
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
}
