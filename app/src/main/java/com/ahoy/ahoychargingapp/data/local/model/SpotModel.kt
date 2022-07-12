package com.ahoy.ahoychargingapp.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "spots")
data class SpotModel(
    @ColumnInfo(name = "spot_title") val title: String,
    @PrimaryKey val uuid: String,
    @ColumnInfo(name = "spot_verified") val isRecentlyVerified: Boolean)