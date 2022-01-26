package com.opemweather.test.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.opemweather.test.data.database.converter.InstantConverter
import com.opemweather.test.data.database.dao.PlaceWeatherDao
import com.opemweather.test.data.database.entity.*

@Database(
    entities = [
        PlaceCoordinatesEntity::class,
        PlaceWeatherEntity::class,
        CloudsEntity::class,
        WindEntity::class,
        TemperatureInfoEntity::class,
        CoordinateEntity::class,
        WeatherInfoEntity::class,
        SysEntity::class
    ],
    version = 1,
)
@TypeConverters(
    InstantConverter::class
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun getPlaceWeatherDao(): PlaceWeatherDao
}