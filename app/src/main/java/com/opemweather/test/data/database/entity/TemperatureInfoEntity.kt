package com.opemweather.test.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.opemweather.test.data.network.model.TemperatureInfo

@Entity(tableName = "temperature_info")
class TemperatureInfoEntity (
    @PrimaryKey
    @ColumnInfo(name = "place_id")
    val placeId: Long,
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

fun TemperatureInfo.convertToEntity(placeId: Long) = TemperatureInfoEntity(
    placeId,
    temperature,
    feelsLike,
    temperatureMin,
    temperatureMax,
    pressure,
    humidity
)