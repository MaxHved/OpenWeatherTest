package com.opemweather.test.data.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TemperatureInfo(
    @Json(name = "temp")
    val temperature: Float,
    @Json(name = "feels_like")
    val feelsLike: Float,
    @Json(name = "temp_min")
    val temperatureMin: Float,
    @Json(name = "temp_max")
    val temperatureMax: Float,
    @Json(name = "pressure")
    val pressure: Int,
    @Json(name = "humidity")
    val humidity: Int,
)