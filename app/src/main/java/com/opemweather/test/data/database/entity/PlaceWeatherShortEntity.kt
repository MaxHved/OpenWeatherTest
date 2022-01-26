package com.opemweather.test.data.database.entity

import androidx.room.Embedded
import androidx.room.Relation

data class PlaceWeatherShortEntity(
    @Embedded
    val placeCoordinatesEntity: PlaceCoordinatesEntity,
    @Relation(
        parentColumn = "place_id",
        entityColumn = "id"
    )
    val placeWeatherEntity: PlaceWeatherEntity?,
    @Relation(
        parentColumn = "place_id",
        entityColumn = "place_id"
    )
    val temperatureInfoEntity: TemperatureInfoEntity?,
    @Relation(
        parentColumn = "place_id",
        entityColumn = "place_id"
    )
    val coordinatesEntity: CoordinateEntity?,
    @Relation(
        parentColumn = "place_id",
        entityColumn = "place_id"
    )
    val weatherInfoEntity: List<WeatherInfoEntity>?
)