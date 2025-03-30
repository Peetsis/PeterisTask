package com.peteris.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.peteris.data.helper.ConnectionMonitor
import com.peteris.data.repository.SportsRepository
import com.peteris.list.state.ListAction
import com.peteris.list.state.ListResult
import com.peteris.list.state.ListState
import com.peteris.list.state.RankingSeason
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ListViewModel(
    private val sportsRepository: SportsRepository,
    private val connectionMonitor: ConnectionMonitor
) : ViewModel() {

    private val _state = MutableStateFlow(ListState())
    val state: StateFlow<ListState> = _state.asStateFlow()

    init {
        initialLoad()
    }

    private fun initialLoad() {
        viewModelScope.launch(Dispatchers.IO) {
            val stateSeason: RankingSeason =
                sportsRepository.getLocalTeams().firstOrNull()?.season?.let {
                    RankingSeason.entries.first { entry ->
                        entry.season == it
                    }
                } ?: state.value.rankingSeason
            loadList(stateSeason)
            connectionMonitor.isOnline.collectLatest { setState(ListResult.SetIsOnline(it)) }
        }
    }

    private fun loadList(rankingSeason: RankingSeason) {
        setState(ListResult.SetLoading)
        viewModelScope.launch {
            sportsRepository.getRemoteTeams(
                season = rankingSeason.season
            ).fold(
                onSuccess = { data ->
                    setState(ListResult.SetIsOnline(true))
                    setState(ListResult.SetSeason(rankingSeason))
                    setState(
                        ListResult.SetListLoaded(
                            listData = data.sortedBy { it.position },
                        )
                    )
                },
                onFailure = {
                    setState(ListResult.SetIsOnline(false))
                    sportsRepository.getLocalTeams()
                        .takeIf { it.isNotEmpty() }?.let { data ->
                            setState(ListResult.SetSeason(rankingSeason))
                            setState(
                                ListResult.SetListLoaded(
                                    listData = data.sortedBy { it.position },
                                )
                            )
                        } ?: setState(ListResult.SetError)
                }
            )
        }
    }

    fun onAction(action: ListAction) = when (action) {
        ListAction.LoadList -> loadList(state.value.rankingSeason)
        ListAction.Refresh -> {
            setState(ListResult.SetRefreshing)
            loadList(state.value.rankingSeason)
        }

        is ListAction.ChangeSeason -> {
            loadList(action.season)
        }
    }

    private fun setState(result: ListResult) {
        _state.update {
            when (result) {
                is ListResult.SetError -> it.copy(
                    error = true,
                    isLoading = false,
                    isRefreshing = false
                )

                is ListResult.SetListLoaded -> it.copy(
                    error = false,
                    isLoading = false,
                    isRefreshing = false,
                    itemList = result.listData
                )

                is ListResult.SetSeason -> it.copy(rankingSeason = result.rankingSeason)
                is ListResult.SetIsOnline -> it.copy(isOnline = result.isOnline)
                ListResult.SetLoading -> it.copy(isLoading = true)
                ListResult.SetRefreshing -> it.copy(isRefreshing = true)
            }
        }
    }
}