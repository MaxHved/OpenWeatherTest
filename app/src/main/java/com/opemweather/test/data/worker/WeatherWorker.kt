package com.opemweather.test.data.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.opemweather.test.data.datasource.PlaceWeatherDataSource
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import timber.log.Timber

const val KEY_LATITUDE = "key_latitude_id"
const val KEY_LONGITUDE = "key_longitude_id"

@HiltWorker
class WeatherWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val placeWeatherDataSource: PlaceWeatherDataSource
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        Timber.i("doWork")
        val latitude: Double = inputData.keyValueMap[KEY_LATITUDE] as Double
        val longitude: Double = inputData.keyValueMap[KEY_LONGITUDE] as Double
        return if (placeWeatherDataSource.updateWorker(latitude, longitude)) {
            Result.success()
        } else {
            Result.retry()
        }

    }
}