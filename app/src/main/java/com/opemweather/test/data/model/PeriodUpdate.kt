package com.opemweather.test.data.model

enum class PeriodUpdate(
    val minute: Long
) {
    HOUR(60),
    HALF_HOUR(30),
    QUARTER_HOUR(15),
    NONE(0)
}