package com.peteris.data.testdata

import com.peteris.data.repository.SportsRepository
import com.peteris.model.Driver
import kotlinx.coroutines.flow.MutableStateFlow

class TestSportsRepository : SportsRepository {

    val testSeason = MutableStateFlow(2022)
    val testDrivers: MutableStateFlow<List<Driver>> = MutableStateFlow(
        listOf(
            Driver(
                id = 1,
                name = "John",
                number = 1,
                driverImage = "2",
                teamName = "Team2",
                teamLogo = "img",
                position = 1,
                wins = 12,
                points = 12.0,
                season = testSeason.value
            ),
            Driver(
                id = 2,
                name = "Alex",
                number = 2,
                driverImage = "2",
                teamName = "Team2 Alex",
                teamLogo = "img",
                position = 1,
                wins = 12,
                points = 12.0,
                season = testSeason.value
            )
        )
    )

    val testRemoteDrivers: MutableStateFlow<Result<List<Driver>>> = MutableStateFlow(
        Result.success(
            listOf(
                Driver(
                    id = 3,
                    name = "Mark",
                    number = 4,
                    driverImage = "5",
                    teamName = "Team22",
                    teamLogo = "img",
                    position = 1,
                    wins = 12,
                    points = 12.0,
                    season = testSeason.value
                )
            )
        )
    )


    override suspend fun getRemoteTeams(season: Int): Result<List<Driver>> {
        return testRemoteDrivers.value
    }

    override suspend fun getLocalTeams(): List<Driver> {
        return testDrivers.value
    }

    override suspend fun getLocalDriver(id: Int): Driver {
        return testDrivers.value.first { it.id == id }
    }
}