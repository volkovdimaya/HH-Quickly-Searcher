package ru.practicum.android.diploma.industries.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.industries.domain.IndustriesInteractor
import ru.practicum.android.diploma.industries.domain.api.IndustriesRepository
import ru.practicum.android.diploma.industries.domain.models.Industry

class IndustriesInteractorImpl(private val industriesRepository: IndustriesRepository) : IndustriesInteractor {

    override fun loadIndustries(): Flow<Pair<Int, List<Industry>>> {
        return industriesRepository.loadIndustries()
    }

    override fun getFilterIndustry(): Flow<Industry?> {
        return industriesRepository.getFilterIndustry()
    }

    override fun getSearchList(text: String): Flow<Pair<Int, List<Industry>>> {
        return industriesRepository.getSearchList(text)
    }

    override fun getLocalIndustryList(): Flow<Pair<Int, List<Industry>>> {
        return industriesRepository.getLocalIndustryList()
    }

    override suspend fun clearTableDb() {
        industriesRepository.clearTableDb()
    }

    override fun saveFilterParameter(item: Industry): Flow<Int> {
        return industriesRepository.insertFilterParameter(item)
    }
}
