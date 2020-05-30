package com.example.com.data.entity.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "example_table")
class ExampleEntity(
    @PrimaryKey val id: Int
)