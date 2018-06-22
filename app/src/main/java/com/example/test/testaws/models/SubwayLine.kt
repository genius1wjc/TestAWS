package com.example.test.testaws.models

import com.fasterxml.jackson.annotation.JsonProperty

data class SubwayLine(
        @JsonProperty("line") val line: String,
        @JsonProperty("time") val time: Int,
        @JsonProperty("timeUnit") val timeUnit: Int,
        @JsonProperty("station") val station: String
)