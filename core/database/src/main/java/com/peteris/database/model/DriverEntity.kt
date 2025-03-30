package com.peteris.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.peteris.model.Driver

@Entity(
    tableName = "drivers"
)
data class DriverEntity (
    @PrimaryKey
    val id: Int,
    val name: String,
    val number: Int,
    val driverImage: String,
    val teamName: String,
    val teamLogo: String,
    val position: Int,
    val wins: Int,
    val points: Double,
    val season: Int
)

fun DriverEntity.toLocalDriver() = Driver(
    id = id,
    name = name,
    number = number,
    driverImage = driverImage,
    teamName = teamName,
    teamLogo = teamLogo,
    position = position,
    wins = wins,
    points = points,
    season = season
)