package ru.practicum.android.diploma.favorites.data.local

import android.database.SQLException
import android.util.Log
import ru.practicum.android.diploma.common.data.db.AppDatabase
import ru.practicum.android.diploma.search.data.dto.Response

class DbClient(private val appDatabase: AppDatabase) : LocalClient {

    companion object {
        private const val TAG = "LocalDbClient"
        private const val SUCCESS_CODE = 200
        const val INTERNAL_ERROR_CODE = 500
    }

    override suspend fun doRequest(): Response {
        return try {
            val response = FavoritesResponse(appDatabase.vacancyDao().getFavorites())
            response.apply { resultCode = SUCCESS_CODE }
        } catch (e: SQLException) {
            Log.d(TAG, e.message.toString())
            return Response().apply { resultCode = INTERNAL_ERROR_CODE }
        }
    }

    override suspend fun doAreaRequest(request: AreaRequest): Response {
        return try {
            val response = AreaResponse(appDatabase.areaDao().getArea(request.areaId))
            response.apply { resultCode = SUCCESS_CODE }
        } catch (e: SQLException) {
            Log.d(TAG, e.message.toString())
            Response().apply { resultCode = INTERNAL_ERROR_CODE }
        }
    }
}
