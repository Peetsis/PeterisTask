package com.peteris.list.state

import com.peteris.model.Driver

sealed class ListResult {
    data object SetLoading : ListResult()
    data object SetRefreshing : ListResult()
    data object SetError : ListResult()
    data class SetSeason(val rankingSeason: RankingSeason) : ListResult()
    data class SetListLoaded(val listData: List<Driver>) : ListResult()
    data class SetIsOnline(val isOnline: Boolean) : ListResult()
}