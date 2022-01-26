package com.opemweather.test.presentation.weather.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.opemweather.test.data.database.entity.PlaceWeatherFullEntity
import com.opemweather.test.data.datasource.PlaceWeatherDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherDetailsViewModel @Inject constructor(
    private val placeWeatherDataSource: PlaceWeatherDataSource
) : ViewModel() {

    private val _placeWeather =
        MutableSharedFlow<PlaceWeatherFullEntity>(replay = 1, extraBufferCapacity = 1)
    val placeWeather = _placeWeather.asSharedFlow()

    fun init(placeId: Long) {
        viewModelScope.launch {
            placeWeatherDataSource.subscribeByPlaceId(placeId).collect { entity ->
                _placeWeather.emit(entity)
            }
        }
    }
}