package ru.practicum.android.diploma.favorites.data.impl

import androidx.room.withTransaction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.transform
import ru.practicum.android.diploma.common.data.db.AppDatabase
import ru.practicum.android.diploma.common.domain.models.VacancyShort
import ru.practicum.android.diploma.countries.mapper.CountryMapper.toAreaEntity
import ru.practicum.android.diploma.favorites.data.local.FavoritesResponse
import ru.practicum.android.diploma.favorites.data.local.LocalClient
import ru.practicum.android.diploma.favorites.data.mapper.ShortVacancyMapper
import ru.practicum.android.diploma.favorites.data.mapper.ShortVacancyMapper.toVacancyEntity
import ru.practicum.android.diploma.favorites.domain.api.FavoritesRepository
import ru.practicum.android.diploma.regions.mapper.RegionMapper.toAreaEntity
import ru.practicum.android.diploma.vacancy.domain.models.VacancyDetail

class FavoritesRepositoryImpl(
    private val localClient: LocalClient,
    private val appDatabase: AppDatabase
) : FavoritesRepository {

    private val _stateFlow = MutableSharedFlow<Unit>(replay = 1)
    private val stateFlow = _stateFlow.asSharedFlow()

    override suspend fun notifyDatabaseChanged() {
        _stateFlow.tryEmit(Unit)
    }

    override fun loadFavorites(): Flow<Pair<Int, List<VacancyShort>>> = stateFlow
        .onStart { emit(Unit) }
        .transform {
            val response: FavoritesResponse = localClient.doRead {
                FavoritesResponse(
                    items = appDatabase.vacancyDao().getFavorites()
                )
            }
            val mappedList = if (response.resultCode != INTERNAL_ERROR_CODE) {
                ShortVacancyMapper.mapVacancyEntityToVacancyShort(response.items)
            } else {
                listOf()
            }
            emit(Pair(response.resultCode, mappedList))
        }
        .flowOn(Dispatchers.IO)

    override fun insertFavoriteVacancy(vacancyDetail: VacancyDetail): Flow<Int> = flow {
        val response = localClient.doWrite(vacancyDetail) {
            appDatabase.withTransaction {
                val region = (it as VacancyDetail).workTerritory.regionWork?.toAreaEntity()
                val country = it.workTerritory.country.toAreaEntity()
                val list = mutableListOf(country)
                region?.let {
                    list.add(region)
                }
                appDatabase.areaDao().insertArea(list)
                appDatabase.vacancyDao().insertVacancy(it.toVacancyEntity())
            }
        }
        emit(response.resultCode)
        if (response.resultCode != INTERNAL_ERROR_CODE) {
            notifyDatabaseChanged()
        }
    }.flowOn(Dispatchers.IO)

    override fun deleteFavoriteVacancy(vacancyDetail: VacancyDetail): Flow<Int> = flow {
        val response = localClient.doWrite(vacancyDetail) {
            appDatabase.withTransaction {
                val region = (it as VacancyDetail).workTerritory.regionWork?.toAreaEntity()
                val country = it.workTerritory.country.toAreaEntity()

                if (appDatabase.vacancyDao().getCountCountries(country.countryId.toString()) == 1) {
                    appDatabase.areaDao().deleteArea(country)
                }

                region?.let {
                    if (appDatabase.vacancyDao().getCountCountries(country.countryId.toString()) == 1) {
                        appDatabase.areaDao().deleteArea(country)
                    }
                }
                appDatabase.vacancyDao().deleteVacancy(it.toVacancyEntity())
            }
        }
        emit(response.resultCode)
        if (response.resultCode != INTERNAL_ERROR_CODE) {
            notifyDatabaseChanged()
        }
    }.flowOn(Dispatchers.IO)

    companion object {
        private const val INTERNAL_ERROR_CODE = 500
    }
}
