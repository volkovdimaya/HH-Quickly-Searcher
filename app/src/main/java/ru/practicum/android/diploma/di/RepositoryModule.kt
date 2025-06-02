package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.countries.data.impl.CountryRepositoryNetworkImpl
import ru.practicum.android.diploma.countries.domain.api.CountryRepositoryNetwork
import ru.practicum.android.diploma.favorites.data.impl.FavoritesRepositoryImpl
import ru.practicum.android.diploma.favorites.domain.api.FavoritesRepository
import ru.practicum.android.diploma.filters.data.impl.FilterParametersRepositoryImpl
import ru.practicum.android.diploma.filters.domain.api.FilterParametersRepository
import ru.practicum.android.diploma.industries.data.impl.IndustriesRepositoryImpl
import ru.practicum.android.diploma.industries.domain.api.IndustriesRepository
import ru.practicum.android.diploma.regions.data.impl.RegionsRepositoryImpl
import ru.practicum.android.diploma.regions.domain.api.RegionsRepository
import ru.practicum.android.diploma.search.data.impl.VacanciesRepositoryImpl
import ru.practicum.android.diploma.search.domain.api.VacanciesRepository
import ru.practicum.android.diploma.vacancy.data.impl.VacancyDetailsRepositoryImpl
import ru.practicum.android.diploma.vacancy.domain.api.VacancyDetailsRepository
import ru.practicum.android.diploma.workterritories.data.impl.WorkTerritoryRepositoryImpl
import ru.practicum.android.diploma.workterritories.domain.api.WorkTerritoriesRepository

val repositoryModule = module {

    single<VacanciesRepository> {
        VacanciesRepositoryImpl(get(), get(), get())
    }

    single<FavoritesRepository> {
        FavoritesRepositoryImpl(get(), get())
    }

    single<VacancyDetailsRepository> {
        VacancyDetailsRepositoryImpl(get(), get(), get())
    }

    single<IndustriesRepository> {
        IndustriesRepositoryImpl(get(), get(), get(), get(), get())
    }

    single<CountryRepositoryNetwork> {
        CountryRepositoryNetworkImpl(get(), get())
    }

    single<FilterParametersRepository> {
        FilterParametersRepositoryImpl(get())
    }

    single<RegionsRepository> {
        RegionsRepositoryImpl(get(), get(), get(), get())
    }

    single<WorkTerritoriesRepository> {
        WorkTerritoryRepositoryImpl(get())
    }
}
