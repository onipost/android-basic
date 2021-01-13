package com.example.com.presentation.utils.network_connectivity

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkRequest

class NetworkConnectivityManager(context: Context) {

    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    private var networkCallback: NetworkCallbackListener? = null

    fun startListen(callback: NetworkCallback) {
        networkCallback = NetworkCallbackListener(connectivityManager, callback)
        connectivityManager.registerNetworkCallback(buildRequest(), networkCallback!!)
    }

    fun stopListen() {
        networkCallback?.apply {
            connectivityManager.unregisterNetworkCallback(this)
            dispose()
        }
        networkCallback = null
    }

    fun isConnected() = connectivityManager.allNetworks.isNotEmpty()

    private fun buildRequest() = NetworkRequest.Builder()
        .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
        .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
        .build()
}
