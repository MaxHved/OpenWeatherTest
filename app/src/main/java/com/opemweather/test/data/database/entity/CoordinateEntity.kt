package com.opemweather.test.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.opemweather.test.data.network.model.Coordinate

@Entity(tableName = "coordinate")
data class CoordinateEntity(
    @PrimaryKey
    @ColumnInfo(name = "place_id")
    val placeId: Long,
    @ColumnInfo(name = "latitude")
    val latitude: Float,
    @ColumnInfo(name = "longitude")
    val longitude: Float,
)

fun Coordinate.convertToEntity(placeId: Long) = CoordinateEntity(
    placeId,
    latitude,
    longitude
)
