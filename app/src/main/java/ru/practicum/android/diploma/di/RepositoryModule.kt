package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.favorites.data.impl.FavoritesRepositoryImpl
import ru.practicum.android.diploma.favorites.domain.api.FavoritesRepository
import ru.practicum.android.diploma.search.data.impl.SearchRepositoryImpl
import ru.practicum.android.diploma.search.data.impl.VacancyRepositoryImpl
import ru.practicum.android.diploma.search.domain.api.SearchRepository
import ru.practicum.android.diploma.search.domain.api.VacancyRepository

val repositoryModule = module {

    single<VacancyRepository> {
        VacancyRepositoryImpl(
            get(), get()
        )
    }

    single<FavoritesRepository> {
        FavoritesRepositoryImpl(get())
    }

    single<SearchRepository> {
        SearchRepositoryImpl(get(), get())
    }
}
