package ru.practicum.android.diploma.search.data.impl

import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.search.data.network.NetworkClient
import ru.practicum.android.diploma.search.domain.VacancyRepository
import ru.practicum.android.diploma.search.domain.models.Vacancy

class VacancyRepositoryImpl(
    private val networkClient: NetworkClient,
    private val gson: Gson,
) : VacancyRepository {

    override fun searchVacancy(
        text: String,
        area: String?,
        professionalRole: String?,
        industry: String?,
        salary: Int?,
        onlyWithSalary: Boolean,
        page: Int,
        perPage: Int
    ): Flow<List<Vacancy>?> = flow {
        emit(null)
    }

    override fun getVacancyDetails(id: String): Flow<Vacancy?> = flow {
        emit(null)
    }
}
