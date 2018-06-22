package com.example.test.testaws.models

import com.fasterxml.jackson.annotation.JsonProperty

data class TestPostResponse(
        @JsonProperty("statusCode") val statusCode: Int,
        @JsonProperty("message") val message: String
)