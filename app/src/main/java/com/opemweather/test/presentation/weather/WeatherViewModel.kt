package com.opemweather.test.presentation.weather

import androidx.lifecycle.viewModelScope
import com.github.terrakok.cicerone.Router
import com.opemweather.test.data.database.entity.PlaceWeatherShortEntity
import com.opemweather.test.data.datasource.PlaceWeatherDataSource
import com.opemweather.test.presentation.base.BaseViewModel
import com.opemweather.test.presentation.screens.Screens
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    router: Router,
    private val dataSource: PlaceWeatherDataSource
) : BaseViewModel(router) {

    private val _placeWeathers =
        MutableSharedFlow<List<PlaceWeatherShortEntity>>(replay = 1, extraBufferCapacity = 1)
    val placeWeathers = _placeWeathers.asSharedFlow()

    init {
        viewModelScope.launch {
            dataSource.subscribeAll().collect { items -> _placeWeathers.emit(items) }
        }
    }

    fun pickPlace() {
        router.navigateTo(Screens.placePicker())
    }

    fun forwardSettings() {
        router.navigateTo(Screens.settings())
    }

    fun refreshPlaceWeather(item: PlaceWeatherShortEntity) {
        viewModelScope.launch {
            dataSource.refresh(item)
        }
    }
}