package com.peteris.model.network

import kotlinx.serialization.Serializable

@Serializable
data class DriversResponse(
    val response: List<DriversData>
)

@Serializable
data class DriversData(
    val position: Int?,
    val points: Double?,
    val wins: Int?,
    val driver: DriverData?,
    val team: TeamData?,
    val season: Int?
)

@Serializable
data class DriverData(
    val id: Int?,
    val name: String?,
    val number: Int?,
    val image: String?
)

@Serializable
data class TeamData(
    val name: String?,
    val logo: String?
)