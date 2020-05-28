package com.example.com.presentation.utils.network_connectivity

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposables
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import timber.log.Timber
import java.util.concurrent.TimeUnit

class NetworkCallbackListener(
    private val connectivityManager: ConnectivityManager,
    private val listener: NetworkCallback
) : ConnectivityManager.NetworkCallback() {

    private val isAvailable = BehaviorSubject.create<Unit>()
    private var disposable = Disposables.disposed()

    init {
        isAvailable.onNext(Unit)
        disposable = isAvailable.debounce(2, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    if (isNetworkAvailable()) {
                        listener.onNetworkConnected()
                    } else {
                        listener.onNetworkDisconnect()
                    }
                },
                { Timber.e(it) }
            )
    }

    override fun onAvailable(network: Network) {
        super.onAvailable(network)
        isAvailable.onNext(Unit)
    }

    override fun onCapabilitiesChanged(network: Network, networkCapabilities: NetworkCapabilities) {
        super.onCapabilitiesChanged(network, networkCapabilities)
        isAvailable.onNext(Unit)
    }

    override fun onLost(network: Network) {
        super.onLost(network)
        isAvailable.onNext(Unit)
    }

    fun dispose() {
        disposable.dispose()
    }

    private fun isNetworkAvailable() =
        connectivityManager.allNetworks.any { network -> checkNetwork(network) }

    private fun checkNetwork(network: Network) =
        connectivityManager.getNetworkCapabilities(network)?.run {
            hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
        } ?: false
}