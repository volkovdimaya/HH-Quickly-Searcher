package ru.practicum.android.diploma.favorites.data.impl

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.transform
import ru.practicum.android.diploma.common.domain.models.VacancyShort
import ru.practicum.android.diploma.common.domain.models.WorkTerritory
import ru.practicum.android.diploma.countries.mapper.CountryMapper.toCountry
import ru.practicum.android.diploma.favorites.data.local.AreaRequest
import ru.practicum.android.diploma.favorites.data.local.AreaResponse
import ru.practicum.android.diploma.favorites.data.local.FavoritesResponse
import ru.practicum.android.diploma.favorites.data.local.LocalClient
import ru.practicum.android.diploma.favorites.data.mapper.ShortVacancyMapper
import ru.practicum.android.diploma.favorites.domain.api.FavoritesRepository
import ru.practicum.android.diploma.regions.mapper.RegionMapper.toRegion
import ru.practicum.android.diploma.workterritories.mapper.WorkTerritoriesMapper

class FavoritesRepositoryImpl(private val localClient: LocalClient) : FavoritesRepository {

    private val _stateFlow = MutableSharedFlow<Unit>(replay = 1)
    private val stateFlow = _stateFlow.asSharedFlow()

    override suspend fun notifyDatabaseChanged() {
        _stateFlow.tryEmit(Unit)
    }

    override fun loadFavorites(): Flow<Pair<Int, List<VacancyShort>>> = stateFlow
        .onStart { emit(Unit) }
        .transform {
            val response = localClient.doRequest()
            val pair = if (response.resultCode != INTERNAL_ERROR_CODE) {
                val items = (response as FavoritesResponse).items
                val mappedList = ShortVacancyMapper.mapVacancyEntityToVacancyShort(items) { areaId ->
                    WorkTerritoriesMapper.createWorkTerritory(localClient, areaId)
                }

                Pair(response.resultCode, mappedList)
            } else {
                Pair(response.resultCode, listOf<VacancyShort>())
            }
            emit(pair)
        }
        .flowOn(Dispatchers.IO)

    companion object {
        private const val INTERNAL_ERROR_CODE = 500
    }
}
