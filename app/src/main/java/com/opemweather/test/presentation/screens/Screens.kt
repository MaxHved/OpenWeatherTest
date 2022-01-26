package com.opemweather.test.presentation.screens

import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.opemweather.test.presentation.picker.PlacePickerFragment
import com.opemweather.test.presentation.weather.WeatherFragment
import com.opemweather.test.presentation.settings.SettingWrapperFragment

object Screens {

    fun weather() = FragmentScreen(key = "PlacesScreen") {
        WeatherFragment.newInstance()
    }

    fun placePicker() = FragmentScreen(key = "PlacePickerScreen") {
        PlacePickerFragment.newInstance()
    }

    fun settings() = FragmentScreen(key = "SettingsScreen") {
        SettingWrapperFragment.newInstance()
    }
}