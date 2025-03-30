package com.peteris.data.testdata

import com.peteris.data.helper.ConnectionMonitor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class TestConnectionMonitor : ConnectionMonitor {

    val online = MutableStateFlow(true)

    override val isOnline: Flow<Boolean> = online
}