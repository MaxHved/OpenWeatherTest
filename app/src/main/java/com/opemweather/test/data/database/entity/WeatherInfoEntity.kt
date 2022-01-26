package com.opemweather.test.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.opemweather.test.data.network.model.Weather

@Entity(
    tableName = "weather_info",
    primaryKeys = ["place_id", "id"]
)
data class WeatherInfoEntity(
    @ColumnInfo(name = "place_id")
    val placeId: Long,
    @ColumnInfo(name = "id")
    val id: Long,
    @ColumnInfo(name = "main")
    val main: String, // TODO enum
    @ColumnInfo(name = "description")
    val description: String,
    @ColumnInfo(name = "icon")
    val icon: String
)

fun Weather.convertToEntity(placeId: Long) = WeatherInfoEntity(
    placeId,
    id,
    main,
    description,
    icon
)

fun List<Weather>.convertToEntity(placeId: Long) =
    map { weather -> weather.convertToEntity(placeId) }
