package com.peteris.data.repository

import com.peteris.data.helper.toEntity
import com.peteris.database.dao.DriverDao
import com.peteris.database.model.DriverEntity
import com.peteris.database.model.toLocalDriver
import com.peteris.model.Driver
import com.peteris.model.network.DriversResponse
import com.peteris.network.SportsApiService
import io.ktor.client.call.body
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface SportsRepository {
    suspend fun getRemoteTeams(season: Int): Result<List<Driver>>
    suspend fun getLocalTeams(): List<Driver>
    suspend fun getLocalDriver(id: Int): Driver
}

class SportsRepositoryImpl(
    private val service: SportsApiService,
    private val driverDao: DriverDao
) : SportsRepository {

    override suspend fun getLocalTeams(): List<Driver> = withContext(Dispatchers.IO) {
        driverDao.getDrivers().map(DriverEntity::toLocalDriver)
    }

    override suspend fun getLocalDriver(id: Int): Driver = withContext(Dispatchers.IO) {
        driverDao.getDriver(id).toLocalDriver()
    }

    override suspend fun getRemoteTeams(season: Int): Result<List<Driver>> = runCatching {
        withContext(Dispatchers.IO) {
            val response = service.getList(season)
            val result = response.body<DriversResponse>().response
                .mapNotNull { data ->
                    Driver(
                        id = data.driver?.id ?: return@mapNotNull null,
                        name = data.driver?.name ?: return@mapNotNull null,
                        number = data.driver?.number ?: return@mapNotNull null,
                        driverImage = data.driver?.image?.replace("\\/", "/")
                            ?: return@mapNotNull null,
                        teamLogo = data.team?.logo?.replace("\\/", "/") ?: return@mapNotNull null,
                        teamName = data.team?.name ?: return@mapNotNull null,
                        position = data.position ?: return@mapNotNull null,
                        points = data.points ?: return@mapNotNull null,
                        wins = data.wins ?: return@mapNotNull null,
                        season = data.season ?: return@mapNotNull null
                    )
                }

            if (response.status == HttpStatusCode.OK && result.isNotEmpty()) {
                driverDao.deleteOldAndInsertNew(result.map(Driver::toEntity))
            }
            result
        }
    }
}