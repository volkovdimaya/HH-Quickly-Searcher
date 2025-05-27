package ru.practicum.android.diploma.favorites.data.local

import ru.practicum.android.diploma.common.data.dto.Response

interface LocalClient {
    suspend fun <T : Response> doRead(action: suspend () -> T): T
    suspend fun doWrite(entity: Any, action: suspend (entity: Any) -> Unit): Response
    suspend fun doUpdate(entity: Any, action: suspend (entity: Any) -> Unit): Response
}
