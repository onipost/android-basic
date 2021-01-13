package com.example.com.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.com.data.database.dao.ExampleDao
import com.example.com.data.entity.database.ExampleEntity

@Database(
    entities = [ExampleEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDB : RoomDatabase() {
    abstract fun exampleDao(): ExampleDao
}
