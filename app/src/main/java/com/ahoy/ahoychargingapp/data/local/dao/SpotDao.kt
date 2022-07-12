package com.ahoy.ahoychargingapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.ahoy.ahoychargingapp.data.local.model.SpotModel

@Dao
interface SpotDao {

    @Query("SELECT * FROM spots")
    fun getAll(): List<SpotModel>

    @Insert
    fun insertAll(spots: List<SpotModel>)
}