package com.ahoy.ahoychargingapp.features.chargingpoints

import com.ahoy.ahoychargingapp.data.ChargingPointsResponse
import com.ahoy.ahoychargingapp.data.ui.ChargingPointModel

data class ChargingPointsViewState(
    val isLoading: Boolean = false,
    val data: List<ChargingPointModel>? = null,
    val error: Throwable? = null
)