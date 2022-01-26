package com.opemweather.test.data.datasource

import com.opemweather.test.data.model.PeriodUpdate
import com.opemweather.test.data.model.Units
import kotlinx.coroutines.flow.Flow

interface PreferenceDataSource {
    fun getUnits(): Units
    fun getPeriodUpdate(): PeriodUpdate
    fun subscribeChangeAll(): Flow<Unit>
}