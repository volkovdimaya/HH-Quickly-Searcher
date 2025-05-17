package ru.practicum.android.diploma.search.data.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.common.domain.models.VacancyShort
import ru.practicum.android.diploma.search.data.dto.VacanciesRequest
import ru.practicum.android.diploma.search.data.dto.VacanciesResponse
import ru.practicum.android.diploma.search.data.network.NetworkClient
import ru.practicum.android.diploma.search.domain.api.VacancyRepository
import ru.practicum.android.diploma.search.mapper.ShortVacancyResponseMapper
import ru.practicum.android.diploma.util.Resource
import ru.practicum.android.diploma.vacancy.domain.models.VacancyDetail

class VacancyRepositoryImpl(
    private val networkClient: NetworkClient,
    private val vacancyResponseMapper: ShortVacancyResponseMapper
) : VacancyRepository {

    override fun searchVacancies(
        vacanciesRequest : VacanciesRequest
    ): Flow<Resource<List<VacancyShort>>> = flow {
        val response = networkClient.doRequest(vacanciesRequest)

        when (response.resultCode) {
            200 -> (response as VacanciesResponse)
                .items.map(vacancyResponseMapper::map)
                .let { emit(Resource.Success(it)) }

            404 -> emit(Resource.Error(404))
            else -> emit(Resource.Error(400))

        }
    }

    override fun getVacancyDetails(id: String): Flow<VacancyDetail?> = flow {
        emit(null)
    }
}
