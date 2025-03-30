package com.peteris.data.helper

import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkCapabilities
import android.os.Build
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

interface ConnectionMonitor {
    val isOnline: Flow<Boolean>
}

internal class ConnectionMonitorImpl(
    context: Context
) : ConnectionMonitor {
    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    private val _isOnline = MutableSharedFlow<Boolean>(replay = 1)
    override val isOnline: Flow<Boolean> get() = _isOnline

    init {
        startMonitoringNetwork()
    }

    private fun startMonitoringNetwork() {
        connectivityManager.registerDefaultNetworkCallback(object :
            NetworkCallback() {
            override fun onAvailable(network: Network) {
                _isOnline.tryEmit(true)
            }

            override fun onLost(network: Network) {
                _isOnline.tryEmit(false)
            }
        })
    }
}