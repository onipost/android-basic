package com.example.com.data.repository

import io.reactivex.Flowable
import com.example.com.domain.repository.ExampleRepository
import com.example.com.data.data_source.example.ExampleCache
import com.example.com.data.data_source.example.ExampleRemote
import com.example.com.data.entity.database.ExampleEntity

class ExampleRepositoryImpl(
    private val localSource: ExampleCache,
    private val remoteSource: ExampleRemote
) : ExampleRepository {

    override fun getCategory(id: Int): Flowable<ExampleEntity> {
        /**
         * Get data from [localSource] or [remoteSource]
         */
        return localSource.get(id)
    }

}