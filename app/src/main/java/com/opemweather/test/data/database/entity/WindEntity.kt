package com.opemweather.test.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.opemweather.test.data.network.model.Wind

@Entity(tableName = "wind")
data class WindEntity(
    @PrimaryKey
    @ColumnInfo(name = "place_id")
    val placeId: Long,
    @ColumnInfo(name = "speed")
    val speed: Float,
    @ColumnInfo(name = "degrees")
    val degrees: Float,
)

fun Wind.convertToEntity(placeId: Long) = WindEntity(
    placeId,
    speed,
    degrees
)