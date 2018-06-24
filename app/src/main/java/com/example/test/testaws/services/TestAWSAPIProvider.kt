package com.example.test.testaws.services

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

object TestAWSAPIProvider {

    private const val TOKYO_END_POINT = "https://gj9lje8h48.execute-api.ap-northeast-1.amazonaws.com/prod/"
    private const val SINGAPORE_END_POINT = "https://hc2zs0cen6.execute-api.ap-southeast-1.amazonaws.com/prod/"
    private const val CALIFORNIA_END_POINT = "https://ljq0dfnlzl.execute-api.us-west-1.amazonaws.com/prod/"

    private var tokyoAPI: TestAWSAPI? = null
    private var singaporeAPI: TestAWSAPI? = null
    private var californiaAPI: TestAWSAPI? = null

    private fun getTokyoAPI(): TestAWSAPI {
        val retrofit = Retrofit.Builder()
                .baseUrl(TOKYO_END_POINT)
                .addConverterFactory(JacksonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        return retrofit.create(TestAWSAPI::class.java)
    }

    private fun getSingaporeAPI(): TestAWSAPI {
        val retrofit = Retrofit.Builder()
                .baseUrl(SINGAPORE_END_POINT)
                .addConverterFactory(JacksonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        return retrofit.create(TestAWSAPI::class.java)
    }

    private fun getCaliforniaAPI(): TestAWSAPI {
        val retrofit = Retrofit.Builder()
                .baseUrl(CALIFORNIA_END_POINT)
                .addConverterFactory(JacksonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        return retrofit.create(TestAWSAPI::class.java)
    }

    fun getRegionAPI(region: String): TestAWSAPI? =
            when (region) {
                "东京" -> if (tokyoAPI == null) getTokyoAPI() else tokyoAPI
                "新加坡" -> if (singaporeAPI == null) getSingaporeAPI() else singaporeAPI
                "加州" -> if (californiaAPI == null) getCaliforniaAPI() else californiaAPI
                else -> null
            }

}