package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.search.domain.impl.InteractorSearchListVacancyImpl
import ru.practicum.android.diploma.search.presentation.api.InteractorSearchListVacancy

val interactorModule = module {
    single<InteractorSearchListVacancy> {
        InteractorSearchListVacancyImpl(get())
    }
}
