package com.example.com.data.repository.di

import dagger.Module
import dagger.Provides
import com.example.com.domain.repository.ExampleRepository
import com.example.com.data.api.ApiExample
import com.example.com.data.data_source.example.ExampleCache
import com.example.com.data.data_source.example.ExampleRemote
import com.example.com.data.database.AppDB
import com.example.com.data.repository.ExampleRepositoryImpl
import com.example.com.data.di.PerApplication

@Module
class ExampleRepositoryModule {

    @PerApplication
    @Provides
    fun provideRepository(cache: ExampleCache, remote: ExampleRemote): ExampleRepository =
        ExampleRepositoryImpl(cache, remote)

    @PerApplication
    @Provides
    fun provideLocalSource(database: AppDB) = ExampleCache(database.exampleDao())

    @PerApplication
    @Provides
    fun provideRemoteSource(api: ApiExample) = ExampleRemote(api)
}