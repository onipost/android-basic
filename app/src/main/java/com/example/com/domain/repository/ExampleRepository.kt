package com.example.com.domain.repository

import io.reactivex.Flowable
import com.example.com.data.entity.database.ExampleEntity

/**
 * Example of repository interface.
 * Regular repository pattern. Into implementation need inject cache data source and api data source
 */
interface ExampleRepository {

    fun getCategory(id: Int): Flowable<ExampleEntity>
}