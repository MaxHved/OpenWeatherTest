package com.opemweather.test.data.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.time.Instant

@JsonClass(generateAdapter = true)
data class Sys(
    @Json(name = "type")
    val type: Int?,
    @Json(name = "id")
    val id: Long?,
    @Json(name = "message")
    val message: Float?,
    @Json(name = "country")
    val countryCode: String,
    @Json(name = "sunrise")
    val sunrise: Instant,
    @Json(name = "sunset")
    val sunset: Instant,
)