package ru.practicum.android.diploma.common.ui.fragments

import androidx.viewbinding.ViewBinding
import ru.practicum.android.diploma.databinding.LayoutNoInternetBinding

abstract class ListWithInternetFragment<T, V : ViewBinding> : BaseListFragment<T, V>() {

    private var _noInternetErrorBinding: LayoutNoInternetBinding? = null
    val noInternetErrorBinding get() = _noInternetErrorBinding!!

    override fun onDestroyView() {
        super.onDestroyView()
        _noInternetErrorBinding = null
    }

    override fun initViews() {
        super.initViews()
        _noInternetErrorBinding = LayoutNoInternetBinding.inflate(layoutInflater)
    }

    override fun updateIncludeViewByError() {
        updateIncludeView(noInternetErrorBinding.root)
    }
}
