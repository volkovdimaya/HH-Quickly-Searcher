package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.favorites.domain.impl.FavoritesInteractorImpl
import ru.practicum.android.diploma.favorites.domain.interactors.FavoritesInteractor

val interactorModule = module {

    single<FavoritesInteractor> {
        FavoritesInteractorImpl(get())
    }
}
