package ru.practicum.android.diploma.search.data.impl

import android.app.Application
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import ru.practicum.android.diploma.common.data.dto.Response
import ru.practicum.android.diploma.filters.domain.api.FilterParametersRepository
import ru.practicum.android.diploma.filters.domain.models.FilterParameters
import ru.practicum.android.diploma.search.data.dto.VacanciesRequest
import ru.practicum.android.diploma.search.data.dto.VacanciesResponse
import ru.practicum.android.diploma.search.data.network.NetworkClient
import ru.practicum.android.diploma.search.domain.api.VacanciesRepository
import ru.practicum.android.diploma.search.domain.models.SearchResult
import ru.practicum.android.diploma.search.mapper.ShortVacancyResponseMapper
import ru.practicum.android.diploma.util.isConnectedInternet
import ru.practicum.android.diploma.vacancy.domain.models.VacancyDetail

class VacanciesRepositoryImpl(
    private val networkClient: NetworkClient,
    private val application: Application,
    private val vacancyResponseMapper: ShortVacancyResponseMapper,
    private val filtersRepository : FilterParametersRepository
) : VacanciesRepository {

    companion object {
        private const val TAG = "VacanciesRepositoryImpl"
        private const val INTERNAL_ERROR_CODE = 500
        private const val BAD_REQUEST_CODE = 400
    }



    override fun searchVacancies(
        text: String,
        filters: FilterParameters?,
        page: Int,
    ): Flow<SearchResult> = flow {
        val response = if (!isConnectedInternet(application)) {
            Response().apply { resultCode = INTERNAL_ERROR_CODE }
        } else {
            getNetworkResponse(text, filters, page)
        }

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
                currentPage = response.page,
                resultCode = response.resultCode
            )

            emit(searchResult)
        } else {
            emit(SearchResult(emptyList(), 0, 0, 0, response.resultCode))
        }
    }.flowOn(Dispatchers.IO)

    private suspend fun getNetworkResponse(
        text: String,
        filters: FilterParameters?,
        page: Int,
    ): Response {
        val request = createSearchRequest(text, filters, page)
        return try {
            networkClient.doRequest(request)
        } catch (e: HttpException) {
            Log.d(TAG, e.message.toString())
            Response().apply { resultCode = BAD_REQUEST_CODE }
        }
    }

    override fun getVacancyDetails(id: Int): Flow<VacancyDetail?> = flow {
        emit(null)
    }

    override fun getFilterParameters(): Flow<Pair<Boolean, FilterParameters>>  = flow {

        emit(Pair(false, filtersRepository.getFilterParametersObserver().first()))

        filtersRepository.refreshNotifier.collect {
            emit(Pair(true, filtersRepository.getFilterParametersObserver().first()))
        }
    }

    private fun createSearchRequest(
        text: String,
        filters: FilterParameters?,
        page: Int
    ): VacanciesRequest {

        return VacanciesRequest(
            text = text,
            area = filters?.let { buildAreaParameter(it) },
            industry = filters?.industryId,
            salary = filters?.salary,
            onlyWithSalary = filters?.onlyWithSalary ?: false,
            page = page,
            perPage = 20,
        )
    }

    private fun buildAreaParameter(filters: FilterParameters): String? {
        return listOfNotNull(
            filters.regionId?.toString() ?: filters.countryId?.toString()
        ).takeIf { it.isNotEmpty() }?.joinToString(",")
    }
}
