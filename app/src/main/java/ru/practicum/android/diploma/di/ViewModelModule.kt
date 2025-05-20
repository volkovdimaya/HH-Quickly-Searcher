package ru.practicum.android.diploma.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.vacancy.presentation.api.VacancyDetailsViewModel
import ru.practicum.android.diploma.favorites.presentation.FavoritesViewModel

val viewModelModule = module {

    viewModel {
        VacancyDetailsViewModel(get())
    }

    viewModel {
        FavoritesViewModel(get())
    }
}
