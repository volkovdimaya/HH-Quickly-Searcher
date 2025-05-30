package ru.practicum.android.diploma.common.ui.fragments

import androidx.viewbinding.ViewBinding
import ru.practicum.android.diploma.common.domain.models.VacancyShort
import ru.practicum.android.diploma.common.ui.adapters.ShortVacancyAdapter

abstract class ShortVacancyFragment<T : ViewBinding> : ListWithSearchFragment<VacancyShort, T>() {

    abstract override val adapter: ShortVacancyAdapter

    open fun initShortVacancyListView() {
        initListView()
    }
}
