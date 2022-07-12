package com.ahoy.ahoychargingapp.di

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ahoy.ahoychargingapp.data.local.AhoyDatabase
import com.ahoy.ahoychargingapp.data.local.dao.SpotDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun database(application: Application): AhoyDatabase =
        Room.databaseBuilder(application, AhoyDatabase::class.java, "ahoy-db").build()

    @Provides
    @Singleton
    fun spotDao(database: AhoyDatabase): SpotDao =
        database.spotDao()

}