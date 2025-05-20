package ru.practicum.android.diploma.search.data.impl

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import ru.practicum.android.diploma.common.domain.models.VacancyShort
import ru.practicum.android.diploma.common.ui.models.FilterParameters
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

    override fun searchVacancies(
        text: String,
        filters: FilterParameters?
    ): Flow<List<VacancyShort>> = flow {
        val request = createSearchRequest(text, filters)
        try {
            val response = networkClient.doRequest(request)
            if (response is VacanciesResponse) {
                val vacancyList = response.items.mapNotNull { vacancyDto ->
                    try {
                        vacancyResponseMapper.map(vacancyDto)
                    } catch (e: Exception) {
                        null
                    }
                }
                emit(vacancyList)
            } else {
                emit(emptyList())
            }
        } catch (e: Exception) {
            throw e
        }
    }.flowOn(Dispatchers.IO)

    override fun getVacancyDetails(id: Int): Flow<VacancyDetail?> = flow {
        emit(null)
    }

    private fun createSearchRequest(text: String, filters: FilterParameters?): VacanciesRequest {
        return VacanciesRequest(
            text = text,
            area = filters?.let { buildAreaParameter(it) },
            industry = filters?.industryId?.toString(),
            salary = filters?.salary,
            onlyWithSalary = filters?.onlyWithSalary ?: false,
        )
    }

    private fun buildAreaParameter(filters: FilterParameters): String? {
        return listOfNotNull(
            filters.countryId?.toString(),
            filters.regionId?.toString()
        ).takeIf { it.isNotEmpty() }?.joinToString(",")
    }
}
