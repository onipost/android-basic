package com.example.com.data.data_source.example

import com.example.com.data.api.ApiExample
import com.example.com.data.entity.api.ExampleQuery

class ExampleRemote(private val api: ApiExample) {

    /**
     * Class for some methods for work with api like REST or Firebase Database
     */
    fun get(id: Int) = api.get(ExampleQuery(id))
}
