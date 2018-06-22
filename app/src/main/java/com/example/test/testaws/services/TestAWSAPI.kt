package com.example.test.testaws.services

import com.example.test.testaws.models.TestGetResponse
import com.example.test.testaws.models.TestPostRequest
import com.example.test.testaws.models.TestPostResponse
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query


interface TestAWSAPI {

    @POST("test-aws")
    fun testPost(@Body request: TestPostRequest): Single<TestPostResponse>

    @GET("test-aws")
    fun testGet(@Query("name") name: String, @Query("id") id: Int, @Query("correct") correct: Boolean): Single<TestGetResponse>
}

