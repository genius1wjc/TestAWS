package com.example.test.testaws.models

import com.fasterxml.jackson.annotation.JsonProperty

data class TestPostRequest(
        @JsonProperty("borough") val borough: String,
        @JsonProperty("price") val price: Int,
        @JsonProperty("includeFee") val includeFee: Boolean,
        @JsonProperty("subwayLines") val subwayLines: List<SubwayLine>
)