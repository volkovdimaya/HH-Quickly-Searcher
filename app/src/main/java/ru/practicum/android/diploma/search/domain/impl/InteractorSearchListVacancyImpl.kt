package ru.practicum.android.diploma.search.domain.impl

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.practicum.android.diploma.common.domain.models.VacancyShort
import ru.practicum.android.diploma.search.data.dto.VacanciesRequest
import ru.practicum.android.diploma.search.domain.api.VacancyRepository
import ru.practicum.android.diploma.search.domain.models.DataConsumer
import ru.practicum.android.diploma.search.presentation.api.InteractorSearchListVacancy
import ru.practicum.android.diploma.util.Resource

class InteractorSearchListVacancyImpl(private val rep: VacancyRepository) : InteractorSearchListVacancy {
    override fun loadVacancy(vacanciesRequest : VacanciesRequest): Flow<DataConsumer<List<VacancyShort>>> {

        return rep.searchVacancies(vacanciesRequest).map { result ->
            when (result) {
                is Resource.Success -> {
                    DataConsumer.Success(result.data)
                }

                is Resource.Error -> when (result.error) {
                    404 -> DataConsumer.ResponseNoContent()
                    else -> DataConsumer.ResponseFailure()
                }
            }
        }
    }
}

