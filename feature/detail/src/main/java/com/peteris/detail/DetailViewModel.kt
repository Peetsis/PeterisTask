package com.peteris.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.peteris.data.repository.SportsRepository
import com.peteris.model.Driver
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DetailViewModel(
    private val sportsRepository: SportsRepository,
    private val id: Int
) : ViewModel() {

    private val _state = MutableStateFlow(DetailState())
    val state: StateFlow<DetailState> = _state.asStateFlow()

    init {
        getDriver()
    }

    // I would fetch single from API if
    // the main image would be a large image url, but its not...
    private fun getDriver() {
        viewModelScope.launch {
            val driverData = sportsRepository.getLocalDriver(id)
            _state.update {
                it.copy(driverData = driverData)
            }
        }
    }
}

data class DetailState(
    val driverData: Driver? = null
)