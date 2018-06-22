package com.example.test.testaws.services

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

object APIProvider {

    fun getTestAWSAPI() : TestAWSAPI {
        val retrofit = Retrofit.Builder()
                .baseUrl("https://gj9lje8h48.execute-api.ap-northeast-1.amazonaws.com/prod/")
                .addConverterFactory(JacksonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        return retrofit.create(TestAWSAPI::class.java)
    }

}