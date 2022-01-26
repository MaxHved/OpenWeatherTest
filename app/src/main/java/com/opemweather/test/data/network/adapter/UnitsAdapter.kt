package com.opemweather.test.data.network.adapter

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import com.opemweather.test.data.model.Units

class UnitsAdapter {

    @ToJson
    fun toJson(unit: Units): String = unit.name

    @FromJson
    fun fromJson(value: String): Units = Units.valueOf(value.lowercase())
}