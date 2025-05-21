package ru.practicum.android.diploma.search.data.impl

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import ru.practicum.android.diploma.common.domain.models.VacancyShort
import ru.practicum.android.diploma.filters.data.dto.FilterParametersDto
import ru.practicum.android.diploma.filters.domain.models.FilterParametersDomain
import ru.practicum.android.diploma.filters.domain.models.toDto
import ru.practicum.android.diploma.search.data.dto.VacanciesRequest
import ru.practicum.android.diploma.search.data.dto.VacanciesResponse
import ru.practicum.android.diploma.search.data.network.NetworkClient
import ru.practicum.android.diploma.search.domain.api.VacanciesRepository
import ru.practicum.android.diploma.search.mapper.ShortVacancyResponseMapper
import ru.practicum.android.diploma.vacancy.domain.models.VacancyDetail

class VacanciesRepositoryImpl(
    private val networkClient: NetworkClient,
    private val vacancyResponseMapper: ShortVacancyResponseMapper
) : VacanciesRepository {

    companion object {
        private const val TAG = "VacanciesRepositoryImpl"
    }

    override fun searchVacancies(
        text: String,
        filters: FilterParametersDomain?
    ): Flow<List<VacancyShort>> = flow {
        val request = createSearchRequest(text, filters)
        val response = networkClient.doRequest(request)
        if (response is VacanciesResponse) {
            val vacancyList = response.items.mapNotNull { vacancyDto ->
                try {
                    vacancyResponseMapper.map(vacancyDto)
                } catch (e: IllegalArgumentException) {
                    Log.d(TAG, e.message.toString())
                    null
                }
            }
            emit(vacancyList)
        } else {
            emit(emptyList())
        }
    }.flowOn(Dispatchers.IO)

    override fun getVacancyDetails(id: Int): Flow<VacancyDetail?> = flow {
        emit(null)
    }

    private fun createSearchRequest(text: String, filters: FilterParametersDomain?): VacanciesRequest {
        val filtersDto = filters?.toDto()

        return VacanciesRequest(
            text = text,
            area = filtersDto?.let { buildAreaParameter(it) },
            industry = filtersDto?.industryId?.toString(),
            salary = filtersDto?.salary,
            onlyWithSalary = filtersDto?.onlyWithSalary ?: false,
        )
    }

    private fun buildAreaParameter(filters: FilterParametersDto): String? {
        return listOfNotNull(
            filters.countryId?.toString(),
            filters.regionId?.toString()
        ).takeIf { it.isNotEmpty() }?.joinToString(",")
    }
}
