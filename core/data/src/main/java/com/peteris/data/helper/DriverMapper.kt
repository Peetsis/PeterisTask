package com.peteris.data.helper

import com.peteris.database.model.DriverEntity
import com.peteris.model.Driver

internal fun Driver.toEntity() = DriverEntity(
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