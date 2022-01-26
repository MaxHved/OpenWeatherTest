package com.opemweather.test.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.opemweather.test.data.model.Status
import com.opemweather.test.data.model.Units

@Entity(
    tableName = "place_coordinate",
    primaryKeys = ["latitude", "longitude"]
)
data class PlaceCoordinatesEntity (
    @ColumnInfo(name = "latitude")
    val latitude: Double,
    @ColumnInfo(name = "longitude")
    val longitude: Double,
    @ColumnInfo(name = "units")
    val units: Units,
    @ColumnInfo(name = "status")
    val status: Status,
    @ColumnInfo(name = "place_id", defaultValue = "-1")
    val placeId: Long = -1

)