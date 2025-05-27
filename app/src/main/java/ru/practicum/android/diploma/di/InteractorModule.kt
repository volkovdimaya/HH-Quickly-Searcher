package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.countries.domain.impl.CountryInteractorImpl
import ru.practicum.android.diploma.countries.presentation.api.CountryInteractor
import ru.practicum.android.diploma.favorites.domain.impl.FavoritesInteractorImpl
import ru.practicum.android.diploma.favorites.domain.interactors.FavoritesInteractor
import ru.practicum.android.diploma.industries.domain.IndustriesInteractor
import ru.practicum.android.diploma.industries.domain.impl.IndustriesInteractorImpl
import ru.practicum.android.diploma.search.domain.impl.VacanciesInteractorImpl
import ru.practicum.android.diploma.search.presentation.api.VacanciesInteractor
import ru.practicum.android.diploma.vacancy.domain.api.VacancyDetailsInteractor
import ru.practicum.android.diploma.vacancy.domain.impl.VacancyDetailsInteractorImpl
import ru.practicum.android.diploma.workterritories.domain.impl.InteractorWorkTerritoriesImpl
import ru.practicum.android.diploma.workterritories.presentation.api.InteractorWorkTerritories

val interactorModule = module {

    single<VacancyDetailsInteractor> {
        VacancyDetailsInteractorImpl(get(), get(), get())
    }

    single<VacanciesInteractor> {
        VacanciesInteractorImpl(get(), get())
    }

    single<FavoritesInteractor> {
        FavoritesInteractorImpl(get())
    }

    single<IndustriesInteractor> {
        IndustriesInteractorImpl(get())
    }
    single<CountryInteractor> {
        CountryInteractorImpl(get(), get())
    }
    single<InteractorWorkTerritories> {
        InteractorWorkTerritoriesImpl(get())
    }
}
