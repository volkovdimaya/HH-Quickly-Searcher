package ru.practicum.android.diploma.favorites.data.local

import android.database.SQLException
import android.util.Log
import ru.practicum.android.diploma.search.data.dto.Response

class DbClient : LocalClient {

    companion object {
        private const val TAG = "LocalDbClient"
        private const val SUCCESS_CODE = 200
        const val INTERNAL_ERROR_CODE = 500
    }

    override suspend fun <T : Response> doRead(action: suspend () -> T): T {
        return try {
            action().apply { resultCode = SUCCESS_CODE }
        } catch (e: SQLException) {
            Log.d(TAG, e.message.toString())
            @Suppress("UNCHECKED_CAST")
            Response().apply { resultCode = INTERNAL_ERROR_CODE } as T
        }
    }

    override suspend fun doWrite(entity: Any, action: suspend (entity: Any) -> Unit): Response {
        return try {
            action(entity)
            Response().apply { resultCode = SUCCESS_CODE }
        } catch (e: SQLException) {
            Log.d(TAG, e.message.toString())
            Response().apply { resultCode = INTERNAL_ERROR_CODE }
        }
    }
}
