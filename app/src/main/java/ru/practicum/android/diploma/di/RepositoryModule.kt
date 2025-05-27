package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.countries.data.impl.CountryRepositoryImpl
import ru.practicum.android.diploma.countries.domain.api.CountryRepository
import ru.practicum.android.diploma.favorites.data.impl.FavoritesRepositoryImpl
import ru.practicum.android.diploma.favorites.domain.api.FavoritesRepository
import ru.practicum.android.diploma.industries.data.impl.IndustriesRepositoryImpl
import ru.practicum.android.diploma.industries.domain.api.IndustriesRepository
import ru.practicum.android.diploma.search.data.impl.VacanciesRepositoryImpl
import ru.practicum.android.diploma.search.domain.api.VacanciesRepository
import ru.practicum.android.diploma.vacancy.data.impl.VacancyDetailsRepositoryImpl
import ru.practicum.android.diploma.vacancy.domain.api.VacancyDetailsRepository

val repositoryModule = module {

    single<VacanciesRepository> {
        VacanciesRepositoryImpl(get(), get())
    }

    single<FavoritesRepository> {
        FavoritesRepositoryImpl(get(), get())
    }

    single<VacancyDetailsRepository> {
        VacancyDetailsRepositoryImpl(get(), get(), get())
    }

    single<IndustriesRepository> {
        IndustriesRepositoryImpl(get(), get(), get(), get())
    }

    single<CountryRepository> {
        CountryRepositoryImpl(get(), get())
    }

//    single<FilterParametersRepository> {
//        FilterParametersRepositoryImpl()
//    }
}
