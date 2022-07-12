package com.ahoy.ahoychargingapp.features.chargingpoints

import com.ahoy.ahoychargingapp.data.ChargingPointsResponse

data class ChargingPointsViewState(
    val isLoading: Boolean = false,
    val data: ChargingPointsResponse? = null,
    val error: Throwable? = null
)