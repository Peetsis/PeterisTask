package com.peteris.list.state

sealed class ListAction {
    data object LoadList : ListAction()
    data object Refresh : ListAction()
    data class ChangeSeason(val season: RankingSeason) : ListAction()
}