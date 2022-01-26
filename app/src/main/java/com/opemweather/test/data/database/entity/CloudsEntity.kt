package com.opemweather.test.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.opemweather.test.data.network.model.Clouds

@Entity(tableName = "clouds")
data class CloudsEntity(
    @PrimaryKey
    @ColumnInfo(name = "place_id")
    val placeId: Long,
    @ColumnInfo(name = "all")
    val all: Int,
)

fun Clouds.convertToEntity(placeId: Long) = CloudsEntity(
    placeId,
    all
)