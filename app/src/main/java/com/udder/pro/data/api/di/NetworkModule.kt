package com.example.com.data.api.di

import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import com.example.com.data.Config
import com.example.com.data.api.ApiExample
import com.example.com.data.di.PerApplication

@Module
class NetworkModule {

    @PerApplication
    @Provides
    fun provideConverterFactory(): Converter.Factory = GsonConverterFactory.create()

    @PerApplication
    @Provides
    fun provideCallAdapterFactory(): CallAdapter.Factory =
        RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io())

    @PerApplication
    @Provides
    fun providesRetrofit(
        config: Config,
        converter: Converter.Factory,
        adapter: CallAdapter.Factory
    ): Retrofit = Retrofit.Builder()
        .baseUrl(config.baseApiPath)
        .addConverterFactory(converter)
        .addCallAdapterFactory(adapter)
        .build()

    @PerApplication
    @Provides
    fun provideApiExample(retrofit: Retrofit): ApiExample = retrofit.create(ApiExample::class.java)
}