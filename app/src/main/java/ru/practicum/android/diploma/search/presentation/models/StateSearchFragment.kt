package ru.practicum.android.diploma.search.presentation.models

import ru.practicum.android.diploma.common.domain.models.VacancyShort

sealed class StateSearchFragment {
    object Idle : StateSearchFragment()
    object ErrorNoInternet : StateSearchFragment()
    object ResponseEmpty : StateSearchFragment()
    class Success(val data: List<VacancyShort>) : StateSearchFragment()
}
