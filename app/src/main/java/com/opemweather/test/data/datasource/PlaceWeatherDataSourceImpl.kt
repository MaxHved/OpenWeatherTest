package com.opemweather.test.data.datasource

import android.content.Context
import androidx.work.*
import com.opemweather.test.BuildConfig
import com.opemweather.test.data.database.dao.PlaceWeatherDao
import com.opemweather.test.data.database.entity.PlaceCoordinatesEntity
import com.opemweather.test.data.database.entity.PlaceWeatherFullEntity
import com.opemweather.test.data.database.entity.PlaceWeatherShortEntity
import com.opemweather.test.data.database.entity.convertToEntity
import com.opemweather.test.data.model.PeriodUpdate
import com.opemweather.test.data.model.Status
import com.opemweather.test.data.network.WeatherApi
import com.opemweather.test.data.worker.KEY_LATITUDE
import com.opemweather.test.data.worker.KEY_LONGITUDE
import com.opemweather.test.data.worker.WeatherWorker
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PlaceWeatherDataSourceImpl @Inject constructor(
    @ApplicationContext
    private val context: Context,
    private val dao: PlaceWeatherDao,
    private val api: WeatherApi,
    private val preference: PreferenceDataSource
) : PlaceWeatherDataSource {

    private val scope: CoroutineScope = CoroutineScope(Dispatchers.Unconfined)

    init {
        scope.launch {
            preference.subscribeChangeAll().collect {
                Timber.i("reload after change settings")
                val placeCoordinates = dao.findAllPlaceCoordinates()
                placeCoordinates?.forEach { item ->
                    load(item, true)
                }
            }
        }
    }

    override fun addPlace(latitude: Double, longitude: Double) {
        scope.launch {
            val units = preference.getUnits()
            val placeCoordinatesEntity =
                PlaceCoordinatesEntity(latitude, longitude, units, Status.LOADING)
            load(placeCoordinatesEntity, true)
        }
    }

    override fun refresh(item: PlaceWeatherShortEntity) {
        scope.launch {
            val updateItem = item.placeCoordinatesEntity.copy(
                status = Status.LOADING
            )
            dao.insertPlaceCoordinate(updateItem)
            load(updateItem, true)
        }
    }

    private suspend fun load(
        placeCoordinatesEntity: PlaceCoordinatesEntity,
        initWorker: Boolean
    ): Boolean {
        if (initWorker) {
            initWorker(
                placeCoordinatesEntity.latitude,
                placeCoordinatesEntity.longitude
            )
        }
        return try {
            dao.insertPlaceCoordinate(placeCoordinatesEntity.copy(status = Status.LOADING))
            val countryCode = Locale.getDefault().country
            val units = preference.getUnits()
            val response =
                api.getWeather(
                    placeCoordinatesEntity.latitude,
                    placeCoordinatesEntity.longitude,
                    units,
                    countryCode,
                    BuildConfig.API_KEY
                )
            val weatherFullEntity = response.convertToEntity(
                placeCoordinatesEntity.copy(
                    placeId = response.id,
                    status = Status.LOADED,
                    units = units
                )
            )
            dao.insertPlaceWeatherFull(weatherFullEntity)
            true
        } catch (e: Exception) {
            dao.insertPlaceCoordinate(placeCoordinatesEntity.copy(status = Status.FAIL))
            false
        }
    }

    override fun subscribeAll(): Flow<List<PlaceWeatherShortEntity>> =
        dao.findAllDistinctUntilChanged()

    override fun subscribeByPlaceId(placeId: Long): Flow<PlaceWeatherFullEntity> =
        dao.findByPlaceId(placeId)

    override suspend fun updateWorker(latitude: Double, longitude: Double): Boolean {
        val placeCoordinatesEntity = dao.findByPlaceCoordinates(latitude, longitude)
        return if (placeCoordinatesEntity != null) {
            Timber.i("start update")
            load(placeCoordinatesEntity, false)
        } else {
            Timber.i("cancel worker")
            WorkManager.getInstance(context).cancelAllWorkByTag(createWorkTag(latitude, longitude))
            true
        }
    }

    private fun initWorker(latitude: Double, longitude: Double) {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        val inputData = workDataOf(
            KEY_LATITUDE to latitude,
            KEY_LONGITUDE to longitude
        )
        val tagWorker = createWorkTag(latitude, longitude)
        val period = preference.getPeriodUpdate()
        if (period == PeriodUpdate.NONE) {
            val updateWorkRequest =
                PeriodicWorkRequest.Builder(
                    WeatherWorker::class.java,
                    period.minute,
                    TimeUnit.MINUTES,
                    period.minute - 5,
                    TimeUnit.MINUTES
                )
                    .addTag(tagWorker)
                    .setInputData(inputData)
                    .setConstraints(constraints)
                    .build()

            with(WorkManager.getInstance(context)) {
                cancelAllWorkByTag(tagWorker)
                enqueue(updateWorkRequest)
            }
        } else {
            WorkManager.getInstance(context).cancelAllWorkByTag(tagWorker)
        }
    }

    private fun createWorkTag(latitude: Double, longitude: Double): String =
        "$latitude, $longitude"
}