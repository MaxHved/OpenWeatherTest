package com.opemweather.test.data.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Coordinate(
    @Json(name = "lon")
    val latitude: Float,
    @Json(name = "lat")
    val longitude: Float,
)