package com.example.test.testaws.models

import com.fasterxml.jackson.annotation.JsonProperty

data class TestGetResponse(
        @JsonProperty("message") val message: String,
        @JsonProperty("result") val result: Float
)