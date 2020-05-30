package com.example.com.data.database.dao

import androidx.room.*
import io.reactivex.Flowable
import com.example.com.data.entity.database.ExampleEntity

@Dao
interface ExampleDao {

    @Query("SELECT * from example_table WHERE id = :id")
    fun get(id: Int): Flowable<ExampleEntity>
}