package com.example.com.data.data_source.example

import com.example.com.data.database.dao.ExampleDao

class ExampleCache(private val database: ExampleDao) {

    /**
     * Class for some methods for work with database
     */

    fun get(id: Int) = database.get(id)
}
