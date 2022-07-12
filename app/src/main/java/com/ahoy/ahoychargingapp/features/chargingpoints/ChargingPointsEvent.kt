package com.ahoy.ahoychargingapp.features.chargingpoints

sealed interface ChargingPointsEvent

data class ChargingPointClicked(
    val pointId: String
): ChargingPointsEvent

data class GetChargingPoints(
    val lat: Double,
    val lng: Double
): ChargingPointsEvent