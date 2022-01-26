package com.opemweather.test.data.database.entity

import androidx.room.Embedded
import androidx.room.Relation
import com.opemweather.test.data.network.model.PlaceWeatherResponse
import java.time.Instant
import java.util.*

data class PlaceWeatherFullEntity(
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
    val coordinateEntity: CoordinateEntity?,
    @Relation(
        parentColumn = "place_id",
        entityColumn = "place_id"
    )
    val temperatureInfoEntity: TemperatureInfoEntity?,
    @Relation(
        parentColumn = "place_id",
        entityColumn = "place_id"
    )
    val cloudsEntity: CloudsEntity?,
    @Relation(
        parentColumn = "place_id",
        entityColumn = "place_id"
    )
    val windEntity: WindEntity?,
    @Relation(
        parentColumn = "place_id",
        entityColumn = "place_id"
    )
    val weather: List<WeatherInfoEntity>?,
    @Relation(
        parentColumn = "place_id",
        entityColumn = "place_id"
    )
    val sysEntity: SysEntity?
)

fun PlaceWeatherResponse.convertToEntity(placeCoordinatesEntity: PlaceCoordinatesEntity) = PlaceWeatherFullEntity(
    placeCoordinatesEntity,
    convertToPlaceWeatherEntity(),
    coordinate.convertToEntity(id),
    temperatureInfo.convertToEntity(id),
    clouds.convertToEntity(id),
    wind.convertToEntity(id),
    weather.convertToEntity(id),
    sys.convertToEntity(id)
)

fun PlaceWeatherResponse.convertToPlaceWeatherEntity() = PlaceWeatherEntity(
    id,
    placeName,
    timeZone,
    cod,
    visibility,
    dt,
    base,
    Instant.now(),
)