package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.favorites.domain.impl.FavoritesInteractorImpl
import ru.practicum.android.diploma.favorites.domain.interactors.FavoritesInteractor
import ru.practicum.android.diploma.industries.domain.IndustriesInteractor
import ru.practicum.android.diploma.industries.domain.impl.IndustriesInteractorImpl
import ru.practicum.android.diploma.regions.domain.RegionsInteractor
import ru.practicum.android.diploma.regions.domain.impl.RegionsInteractorImpl
import ru.practicum.android.diploma.search.domain.impl.VacanciesInteractorImpl
import ru.practicum.android.diploma.search.presentation.api.VacanciesInteractor
import ru.practicum.android.diploma.vacancy.domain.api.VacancyDetailsInteractor
import ru.practicum.android.diploma.vacancy.domain.impl.VacancyDetailsInteractorImpl
import ru.practicum.android.diploma.workterritories.domain.impl.WorkTerritoryInteractorImpl
import ru.practicum.android.diploma.workterritories.domain.interactor.WorkTerritoryInteractor

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

    single<RegionsInteractor> {
        RegionsInteractorImpl(get())
    }

    single<WorkTerritoryInteractor> {
        WorkTerritoryInteractorImpl(get())
    }
}
