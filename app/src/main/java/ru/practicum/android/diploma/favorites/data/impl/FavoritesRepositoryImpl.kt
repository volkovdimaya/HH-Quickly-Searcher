package ru.practicum.android.diploma.favorites.data.impl

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
import ru.practicum.android.diploma.favorites.data.local.FavoritesResponse
import ru.practicum.android.diploma.favorites.data.local.LocalClient
import ru.practicum.android.diploma.favorites.data.mapper.ShortVacancyMapper
import ru.practicum.android.diploma.favorites.domain.api.FavoritesRepository
import ru.practicum.android.diploma.vacancy.domain.models.VacancyDetail
import ru.practicum.android.diploma.vacancy.mapper.VacancyDetailsMapper

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
            appDatabase.vacancyDao().insertVacancy(
                VacancyDetailsMapper.mapToEntity(it as VacancyDetail)
            )
        }
        emit(response.resultCode)
        if (response.resultCode != INTERNAL_ERROR_CODE) {
            notifyDatabaseChanged()
        }
    }.flowOn(Dispatchers.IO)

    override fun deleteFavoriteVacancy(vacancyDetail: VacancyDetail): Flow<Int> = flow {
        val response = localClient.doWrite(vacancyDetail) {
            appDatabase.vacancyDao().deleteVacancy(
                VacancyDetailsMapper.mapToEntity(it as VacancyDetail)
            )
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
