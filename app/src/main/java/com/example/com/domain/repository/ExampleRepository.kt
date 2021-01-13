package com.example.com.domain.repository

import com.example.com.data.entity.database.ExampleEntity
import io.reactivex.Flowable

/**
 * Example of repository interface.
 * Regular repository pattern. Into implementation need inject cache data source and api data source
 */
interface ExampleRepository {

    fun getCategory(id: Int): Flowable<ExampleEntity>
}
