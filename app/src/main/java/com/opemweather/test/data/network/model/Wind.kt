package com.opemweather.test.data.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Wind(
    @Json(name = "speed")
    val speed: Float,
    @Json(name = "deg")
    val degrees: Float,
)