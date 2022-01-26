package com.opemweather.test.data.datasource

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.opemweather.test.R
import com.opemweather.test.data.model.PeriodUpdate
import com.opemweather.test.data.model.Units
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferenceDataSourceImpl @Inject constructor(
    @ApplicationContext
    context: Context
) : PreferenceDataSource {

    private val scope = CoroutineScope(Dispatchers.Unconfined)

    private val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
    private val changeDataFlow = MutableSharedFlow<Unit>()

    private val unitsKey = context.getString(R.string.preference_key_units)
    private val unitsDefaultValue = context.resources.getStringArray(R.array.units_values).first()
    private val periodUpdateKey = context.getString(R.string.preference_key_period_update)
    private val periodUpdateDefaultValue =
        context.resources.getStringArray(R.array.period_update_values).first()

    private val onSharedPreferenceChangeListener =
        SharedPreferences.OnSharedPreferenceChangeListener { _, _ ->  scope.launch { changeDataFlow.emit(Unit) }}

    init {
        sharedPreferences.registerOnSharedPreferenceChangeListener(onSharedPreferenceChangeListener)
    }

    override fun getUnits(): Units {
        val name = sharedPreferences.getString(unitsKey, unitsDefaultValue) ?: unitsDefaultValue
        return Units.valueOf(name)
    }

    override fun getPeriodUpdate(): PeriodUpdate {
        val name = sharedPreferences.getString(periodUpdateKey, periodUpdateDefaultValue)
            ?: periodUpdateDefaultValue
        return PeriodUpdate.valueOf(name)
    }

    override fun subscribeChangeAll(): Flow<Unit> = changeDataFlow.asSharedFlow()




}