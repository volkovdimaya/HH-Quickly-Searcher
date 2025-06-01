package ru.practicum.android.diploma.search.data.impl

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import ru.practicum.android.diploma.filters.domain.models.FilterParameters
import ru.practicum.android.diploma.filters.mapper.FilterParametersMapper.toDto
import ru.practicum.android.diploma.search.data.dto.FilterParametersDto
import ru.practicum.android.diploma.search.data.dto.VacanciesRequest
import ru.practicum.android.diploma.search.data.dto.VacanciesResponse
import ru.practicum.android.diploma.search.data.network.NetworkClient
import ru.practicum.android.diploma.search.domain.api.VacanciesRepository
import ru.practicum.android.diploma.search.domain.models.SearchResult
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
        filters: FilterParameters?,
        page: Int,
    ): Flow<SearchResult> = flow {
        val request = createSearchRequest(text, filters, page)
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

            val searchResult = SearchResult(
                vacancies = vacancyList,
                found = response.found,
                pages = response.pages,
                currentPage = response.page
            )

            emit(searchResult)
        } else {
            emit(SearchResult(emptyList(), 0, 0, 0))
        }
    }.flowOn(Dispatchers.IO)

    override fun getVacancyDetails(id: Int): Flow<VacancyDetail?> = flow {
        emit(null)
    }

    private fun createSearchRequest(
        text: String,
        filters: FilterParameters?,
        page: Int
    ): VacanciesRequest {
        val filtersDto = filters?.toDto()

        return VacanciesRequest(
            text = text,
            area = filtersDto?.let { buildAreaParameter(it) },
            industry = filtersDto?.industryId?.toString(),
            salary = filtersDto?.salary,
            onlyWithSalary = filtersDto?.onlyWithSalary ?: false,
            page = page,
            perPage = 20,
        )
    }

    private fun buildAreaParameter(filters: FilterParametersDto): String? {
        return listOfNotNull(
            filters.regionId?.toString() ?: filters.countryId?.toString()
        ).takeIf { it.isNotEmpty() }?.joinToString(",")
    }
}
