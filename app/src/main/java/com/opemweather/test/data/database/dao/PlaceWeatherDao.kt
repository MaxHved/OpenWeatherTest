package com.opemweather.test.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Transaction
import com.opemweather.test.data.database.entity.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged

@Dao
interface PlaceWeatherDao {

    @Transaction
    @Query("SELECT * FROM place_coordinate")
    fun findAll(): Flow<List<PlaceWeatherShortEntity>>

    fun findAllDistinctUntilChanged() = findAll().distinctUntilChanged()

    @Query("SELECT * FROM place_coordinate WHERE latitude = :latitude AND longitude = :longitude")
    suspend fun findByPlaceCoordinates(latitude: Double, longitude: Double): PlaceCoordinatesEntity?

    @Query("SELECT * FROM place_coordinate")
    suspend fun findAllPlaceCoordinates(): List<PlaceCoordinatesEntity>?

    @Transaction
    @Query("SELECT * FROM place_coordinate WHERE place_id = :placeId")
    fun findByPlaceId(placeId: Long): Flow<PlaceWeatherFullEntity>

    fun findByPlaceIdDistinctUntilChanged(placeId: Long) =
        findByPlaceId(placeId).distinctUntilChanged()

    @Insert(onConflict = REPLACE)
    suspend fun insertPlaceCoordinate(entity: PlaceCoordinatesEntity)

    @Transaction
    suspend fun insertPlaceWeatherFull(entity: PlaceWeatherFullEntity) {
        with(entity) {
            _insert(placeCoordinatesEntity)
            _insert(placeWeatherEntity)
            _insert(coordinateEntity)
            _insert(temperatureInfoEntity)
            _insert(cloudsEntity)
            _insert(windEntity)
            _deleteWeatherInfo(placeCoordinatesEntity.placeId)
            _insert(weather)
            _insert(sysEntity)
        }
    }

    @Insert(onConflict = REPLACE)
    fun _insert(entity: PlaceCoordinatesEntity)

    @Insert(onConflict = REPLACE)
    fun _insert(entity: PlaceWeatherEntity?)

    @Insert(onConflict = REPLACE)
    fun _insert(entity: CoordinateEntity?)

    @Insert(onConflict = REPLACE)
    fun _insert(entity: TemperatureInfoEntity?)

    @Insert(onConflict = REPLACE)
    fun _insert(entity: CloudsEntity?)

    @Insert(onConflict = REPLACE)
    fun _insert(entity: WindEntity?)

    @Query("DELETE FROM weather_info WHERE place_id = :placeId")
    fun _deleteWeatherInfo(placeId: Long)

    @Insert(onConflict = REPLACE)
    fun _insert(entities: List<WeatherInfoEntity>?)

    @Insert(onConflict = REPLACE)
    fun _insert(entity: SysEntity?)

}