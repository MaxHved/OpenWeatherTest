package com.opemweather.test.di

import android.content.Context
import androidx.room.Room
import com.opemweather.test.data.database.AppDatabase
import com.opemweather.test.data.database.dao.PlaceWeatherDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    fun provideDatabase(
        @ApplicationContext
        context: Context
    ): AppDatabase {
        val databaseName = "database.sqlite"
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            databaseName
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun providePlaceWeatherDao(db: AppDatabase): PlaceWeatherDao = db.getPlaceWeatherDao()

}