package com.opemweather.test.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Instant

@Entity(tableName = "place_weather")
data class PlaceWeatherEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Long,
    @ColumnInfo(name = "name", defaultValue = "")
    val name: String,
    @ColumnInfo(name = "timezone")
    val timeZone: Int,
    @ColumnInfo(name = "cod")
    val cod: Int,
    @ColumnInfo(name = "visibility")
    val visibility: Int,
    @ColumnInfo(name = "dt")
    val dt: Long,
    @ColumnInfo(name = "base")
    val base: String, // TODO enum,
    @ColumnInfo(name = "updated_at")
    val updated: Instant,
)