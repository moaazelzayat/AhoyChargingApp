package com.ahoy.ahoychargingapp.features.chargingpoints

import com.ahoy.ahoychargingapp.activities.RootRoute

sealed interface ChargingPointsEffect

data class ShowError(
    val error: Throwable
): ChargingPointsEffect

data class Route(val target: RootRoute) : ChargingPointsEffect