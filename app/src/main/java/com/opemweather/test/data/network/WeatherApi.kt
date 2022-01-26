package com.opemweather.test.data.network

import com.opemweather.test.data.network.model.PlaceWeatherResponse
import com.opemweather.test.data.model.Units
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("/data/2.5/weather")
    suspend fun getWeather(
        @Query("lat")
        latitude: Double,
        @Query("lon")
        longitude: Double,
        @Query("units")
        units: Units,
        @Query("lang")
        languageCode: String,
        @Query("appid")
        apiKey: String,
    ): PlaceWeatherResponse
}