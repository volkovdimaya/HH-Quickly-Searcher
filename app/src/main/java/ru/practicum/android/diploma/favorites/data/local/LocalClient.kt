package ru.practicum.android.diploma.favorites.data.local

import ru.practicum.android.diploma.search.data.dto.Response

interface LocalClient {
    suspend fun doRequest(): Response
    suspend fun doAreaRequest(request: AreaRequest): Response
}
