package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.favorites.data.impl.FavoritesRepositoryImpl
import ru.practicum.android.diploma.favorites.domain.api.FavoritesRepository
import ru.practicum.android.diploma.search.data.impl.VacancyRepositoryImpl
import ru.practicum.android.diploma.search.domain.VacancyRepository

val repositoryModule = module {

    single<VacancyRepository> {
        VacancyRepositoryImpl(
            networkClient = get(),
            gson = get(),
        )
    }

    single<FavoritesRepository> {
        FavoritesRepositoryImpl(get(), get())
    }
}
