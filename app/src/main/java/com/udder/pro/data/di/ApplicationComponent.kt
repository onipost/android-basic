package com.example.com.data.di

import android.app.Application
import android.content.Context
import com.example.com.data.entity.other.InitializationStatus
import com.example.com.data.rxbus.RxBus
import com.example.com.domain.repository.*
import com.example.com.presentation.App
import dagger.BindsInstance
import dagger.Component
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Named

@PerApplication
@Component(modules = [AppModule::class])
interface ApplicationComponent {

    fun inject(app: App)

    @get:Named("app_context")
    var context: Context

    var rxBus: RxBus

    var exampleRepository: ExampleRepository

    var initializationStatus: BehaviorSubject<InitializationStatus>

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun appModule(appModule: AppModule): Builder

        fun build(): ApplicationComponent
    }
}