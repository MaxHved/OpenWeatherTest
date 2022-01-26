package com.opemweather.test.presentation.picker

import com.github.terrakok.cicerone.Router
import com.google.android.gms.maps.model.LatLng
import com.opemweather.test.data.datasource.PlaceWeatherDataSource
import com.opemweather.test.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PlacePickerViewModel @Inject constructor(
    router: Router,
    private val placeWeatherDataSource: PlaceWeatherDataSource
) : BaseViewModel(router) {

    fun pickPlace(position: LatLng) {
        placeWeatherDataSource.addPlace(position.latitude, position.longitude)
        router.exit()
    }
}