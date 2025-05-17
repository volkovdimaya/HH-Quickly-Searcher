package ru.practicum.android.diploma.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.search.presentation.ViewModelSearch

val viewModelModule = module {
        viewModel{
            ViewModelSearch(get())
        }
}
