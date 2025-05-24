package ru.practicum.android.diploma.industries.domain

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.industries.domain.models.Industry

interface IndustriesInteractor {
    fun loadIndustries(): Flow<Pair<Int, List<Industry>>>
    fun getSearchList(text: String): Flow<Pair<Int, List<Industry>>>
    fun getLocalIndustryList(): Flow<Pair<Int, List<Industry>>>
    suspend fun clearTableDb()
}
