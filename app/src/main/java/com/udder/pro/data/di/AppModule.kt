package com.example.com.data.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Named
import com.example.com.data.Config
import com.example.com.data.api.di.NetworkModule
import com.example.com.data.database.AppDB
import com.example.com.data.entity.other.InitializationStatus
import com.example.com.data.repository.di.*
import com.example.com.data.rxbus.RxBus
import com.example.com.data.utils.inProgress
import com.example.com.presentation.di.UiInjects
import com.example.com.presentation.router.Router
import com.example.com.presentation.utils.NotificationChannelBuilder
import com.example.com.presentation.utils.network_connectivity.NetworkConnectivityManager

@Module(
    includes = [
        NetworkModule::class,
        ExampleRepositoryModule::class,
        UiInjects::class
    ]
)
class AppModule(private val application: Application) {

    @PerApplication
    @Provides
    @Named("app_context")
    fun provideApplicationContext(): Context = application.applicationContext

    @PerApplication
    @Provides
    fun provideDatabase(@Named("app_context") context: Context, config: Config): AppDB =
        Room.databaseBuilder(context, AppDB::class.java, config.databaseName).build()

    @PerApplication
    @Provides
    fun provideRxBus() = RxBus()

    @PerApplication
    @Provides
    fun provideConfig() = Config()

    @PerApplication
    @Provides
    fun provideRouter() = Router()

    @PerApplication
    @Provides
    fun provideNetworkConnectivity(@Named("app_context") context: Context): NetworkConnectivityManager =
        NetworkConnectivityManager(context)

    @PerApplication
    @Provides
    fun provideNotificationChannelBuilder(@Named("app_context") context: Context) =
        NotificationChannelBuilder(context)

    @PerApplication
    @Provides
    fun provideInitStatus(): BehaviorSubject<InitializationStatus> =
        BehaviorSubject.create<InitializationStatus>().apply { inProgress() }

}