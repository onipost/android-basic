package com.example.com.data.api

import com.example.com.data.entity.api.ExampleQuery
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiExample {

    /**
     * Interface for retrofit calls
     */

    @POST("example/endpoint")
    fun get(@Body data: ExampleQuery): Single<Any>
}
