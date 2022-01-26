package com.opemweather.test.data.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Weather(
    @Json(name = "id")
    val id: Long,
    @Json(name = "main")
    val main: String, // TODO enum
    @Json(name = "description")
    val description: String,
    @Json(name = "icon")
    val icon: String
)