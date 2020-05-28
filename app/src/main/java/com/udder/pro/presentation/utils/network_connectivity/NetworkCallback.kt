package com.example.com.presentation.utils.network_connectivity

interface NetworkCallback {

    fun onNetworkDisconnect()

    fun onNetworkConnected()
}