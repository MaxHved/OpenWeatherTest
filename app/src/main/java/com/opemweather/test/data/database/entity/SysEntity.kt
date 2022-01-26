package com.opemweather.test.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.opemweather.test.data.network.model.Sys
import java.time.Instant

@Entity(tableName = "sys")
data class SysEntity(
    @PrimaryKey
    @ColumnInfo(name = "place_id")
    val placeId: Long,
    @ColumnInfo(name = "id")
    val id: Long,
    @ColumnInfo(name = "type")
    val type: Int,
    @ColumnInfo(name = "message")
    val message: Float?,
    @ColumnInfo(name = "country")
    val countryCode: String,
    @ColumnInfo(name = "sunrise")
    val sunrise: Instant,
    @ColumnInfo(name = "sunset")
    val sunset: Instant,
)

fun Sys.convertToEntity(placeId: Long) = SysEntity(
    placeId,
    id ?: -1,
    type ?: -1,
    message,
    countryCode,
    sunrise,
    sunset,
)
