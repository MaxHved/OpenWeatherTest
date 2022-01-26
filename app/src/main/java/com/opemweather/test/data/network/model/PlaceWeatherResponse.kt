package com.opemweather.test.data.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PlaceWeatherResponse(
    @Json(name = "coord")
    val coordinate: Coordinate,
    @Json(name = "weather")
    val weather: List<Weather>,
    @Json(name = "base")
    val base: String, // TODO enum
    @Json(name = "main")
    val temperatureInfo: TemperatureInfo,
    @Json(name = "visibility")
    val visibility: Int,
    @Json(name = "wind")
    val wind: Wind,
    @Json(name = "clouds")
    val clouds: Clouds,
    @Json(name = "dt")
    val dt: Long,
    @Json(name = "sys")
    val sys: Sys,
    @Json(name = "timezone")
    val timeZone: Int,
    @Json(name = "id")
    val id: Long,
    @Json(name = "name")
    val placeName: String,
    @Json(name = "cod")
    val cod: Int,
)