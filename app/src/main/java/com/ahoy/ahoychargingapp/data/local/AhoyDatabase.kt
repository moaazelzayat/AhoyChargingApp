package com.ahoy.ahoychargingapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ahoy.ahoychargingapp.data.local.dao.SpotDao
import com.ahoy.ahoychargingapp.data.local.model.SpotModel

@Database(entities = [SpotModel::class], version = 1)
abstract class AhoyDatabase: RoomDatabase() {
    abstract fun spotDao(): SpotDao
}