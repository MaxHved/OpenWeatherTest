package com.opemweather.test.data.datasource

import com.opemweather.test.data.database.entity.PlaceWeatherFullEntity
import com.opemweather.test.data.database.entity.PlaceWeatherShortEntity
import kotlinx.coroutines.flow.Flow

interface PlaceWeatherDataSource {
    fun addPlace(latitude: Double, longitude: Double)
    fun refresh(item: PlaceWeatherShortEntity)
    fun subscribeAll(): Flow<List<PlaceWeatherShortEntity>>
    fun subscribeByPlaceId(placeId: Long): Flow<PlaceWeatherFullEntity>
    suspend fun updateWorker(latitude: Double, longitude: Double): Boolean
}