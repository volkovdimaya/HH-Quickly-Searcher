package ru.practicum.android.diploma.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.countries.presentation.CountryViewModel
import ru.practicum.android.diploma.favorites.presentation.FavoritesViewModel
import ru.practicum.android.diploma.filters.presentation.api.FiltersViewModel
import ru.practicum.android.diploma.industries.presentation.IndustriesViewModel
import ru.practicum.android.diploma.regions.presentation.RegionsViewModel
import ru.practicum.android.diploma.search.presentation.SearchViewModel
import ru.practicum.android.diploma.vacancy.presentation.api.VacancyDetailsViewModel
import ru.practicum.android.diploma.workterritories.presentation.WorkTerritoriesViewModel

val viewModelModule = module {

    viewModel {
        VacancyDetailsViewModel(get())
    }

    viewModel {
        SearchViewModel(get(),)
    }

    viewModel {
        FavoritesViewModel(get())
    }

    viewModel {
        IndustriesViewModel(get())
    }

    viewModel {
        RegionsViewModel(get())
    }

    viewModel {
        WorkTerritoriesViewModel(get())
    }

    viewModel {
        FiltersViewModel(get())
    }

    viewModel {
        CountryViewModel(get())
    }
}
