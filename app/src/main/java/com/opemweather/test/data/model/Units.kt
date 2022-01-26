package com.opemweather.test.data.model

import androidx.annotation.StringRes
import com.opemweather.test.R

enum class Units(
    @StringRes
    val formatLabel: Int
) {
    STANDARD(
        R.string.temperature_kelvin
    ),
    METRIC(
        R.string.temperature_celsius
    ),
    IMPERIAL(
        R.string.temperature_fahrenheit
    )
}