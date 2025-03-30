package com.peteris.model

import kotlinx.serialization.Serializable

@Serializable
data class Driver(
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

