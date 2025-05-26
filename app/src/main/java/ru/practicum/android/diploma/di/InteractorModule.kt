package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.favorites.domain.impl.FavoritesInteractorImpl
import ru.practicum.android.diploma.favorites.domain.interactors.FavoritesInteractor
import ru.practicum.android.diploma.filters.domain.api.FilterParametersInteractor
import ru.practicum.android.diploma.filters.domain.impl.FilterParametersInteractorImpl
import ru.practicum.android.diploma.industries.domain.IndustriesInteractor
import ru.practicum.android.diploma.industries.domain.impl.IndustriesInteractorImpl
import ru.practicum.android.diploma.search.domain.impl.VacanciesInteractorImpl
import ru.practicum.android.diploma.search.presentation.api.VacanciesInteractor
import ru.practicum.android.diploma.vacancy.domain.api.VacancyDetailsInteractor
import ru.practicum.android.diploma.vacancy.domain.impl.VacancyDetailsInteractorImpl

val interactorModule = module {

    single<VacancyDetailsInteractor> {
        VacancyDetailsInteractorImpl(get(), get(), get())
    }

    single<VacanciesInteractor> {
        VacanciesInteractorImpl(get())
    }

    single<FavoritesInteractor> {
        FavoritesInteractorImpl(get())
    }

    single<IndustriesInteractor> {
        IndustriesInteractorImpl(get())
    }

    single<FilterParametersInteractor> {
        FilterParametersInteractorImpl(get())
    }
}
