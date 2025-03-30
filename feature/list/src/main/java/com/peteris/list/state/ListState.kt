package com.peteris.list.state

import com.peteris.model.Driver

enum class RankingSeason(val season: Int) {
    // Free tier of API only has these available :(
    SEASON_2021(2021), SEASON_2022(2022), SEASON_2023(2023)
}

data class ListState(
    val itemList: List<Driver> = emptyList(),
    val isLoading: Boolean = true,
    val isRefreshing: Boolean = false,
    val error: Boolean = false,
    val rankingSeason: RankingSeason = RankingSeason.SEASON_2021,
    val isOnline: Boolean = true
)