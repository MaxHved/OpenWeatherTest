package com.opemweather.test.di

import com.opemweather.test.data.datasource.PlaceWeatherDataSource
import com.opemweather.test.data.datasource.PlaceWeatherDataSourceImpl
import com.opemweather.test.data.datasource.PreferenceDataSource
import com.opemweather.test.data.datasource.PreferenceDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    abstract fun bindPlaceWaterDataSource(dataSource: PlaceWeatherDataSourceImpl): PlaceWeatherDataSource

    @Binds
    abstract fun bindPreferenceDataSource(dataSource: PreferenceDataSourceImpl): PreferenceDataSource

}