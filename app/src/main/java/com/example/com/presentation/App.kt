package com.example.com.presentation

import android.app.Application
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import timber.log.Timber
import javax.inject.Inject
import com.example.com.data.AppInitializer
import com.example.com.data.di.AppModule
import com.example.com.data.di.ApplicationComponent
import com.example.com.presentation.utils.NotificationChannelBuilder
import com.example.com.presentation.utils.network_connectivity.NetworkConnectivityManager

class App : Application(), HasAndroidInjector {

    lateinit var applicationComponent: ApplicationComponent

    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Any>

    @Inject
    lateinit var appInitializer: AppInitializer

    @Inject
    lateinit var networkConnectivityManager: NetworkConnectivityManager

    @Inject
    lateinit var notificationChannelBuilder: NotificationChannelBuilder

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        initializeDagger()
    }

    override fun androidInjector(): AndroidInjector<Any> = activityInjector

    @Inject
    fun init() {
        appInitializer.init()
        notificationChannelBuilder.createChannel()
    }

    private fun initializeDagger() {
        applicationComponent = DaggerApplicationComponent.builder()
            .application(this)
            .appModule(AppModule(this))
            .build()
        applicationComponent.inject(this)
    }
}