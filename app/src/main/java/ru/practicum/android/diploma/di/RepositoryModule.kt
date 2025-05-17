package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.search.data.impl.VacancyRepositoryImpl
import ru.practicum.android.diploma.search.domain.api.VacancyRepository

val repositoryModule = module {

    single<VacancyRepository> {
        VacancyRepositoryImpl(
            get(), get()
        )
    }
}
