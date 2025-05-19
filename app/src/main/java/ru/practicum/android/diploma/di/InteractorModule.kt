package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.search.domain.impl.InteractorSearchListVacancyImpl
import ru.practicum.android.diploma.search.presentation.api.InteractorSearchListVacancy
import ru.practicum.android.diploma.favorites.domain.impl.FavoritesInteractorImpl
import ru.practicum.android.diploma.favorites.domain.interactors.FavoritesInteractor

val interactorModule = module {

    single<InteractorSearchListVacancy> {
        InteractorSearchListVacancyImpl(get())
    }

    single<FavoritesInteractor> {
        FavoritesInteractorImpl(get())
    }
}
