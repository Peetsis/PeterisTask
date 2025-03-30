package com.peteris.network

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

private const val BASE_URL = "https://v1.formula-1.api-sports.io/"

interface SportsApiService {
    suspend fun getList(season: Int): HttpResponse
}

internal class SportsApiServiceImpl(
    private val client: HttpClient
): SportsApiService {

    override suspend fun getList(season: Int) = withContext(Dispatchers.Default) {
        client.get {
            url(BASE_URL + "rankings/drivers")
            parameter("season", season)
        }
    }
}