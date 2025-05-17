package ru.practicum.android.diploma.search.presentation.models


sealed class StateSearchFragment {
    object Idle : StateSearchFragment()
    object ErrorNoInternet : StateSearchFragment()
    object ResponseEmpty : StateSearchFragment()
    class Success(val data: List<VacancyShortUi>) : StateSearchFragment()
}
