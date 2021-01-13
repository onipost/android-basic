package com.example.com.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.com.data.entity.database.ExampleEntity
import io.reactivex.Flowable

@Dao
interface ExampleDao {

    @Query("SELECT * from example_table WHERE id = :id")
    fun get(id: Int): Flowable<ExampleEntity>
}
