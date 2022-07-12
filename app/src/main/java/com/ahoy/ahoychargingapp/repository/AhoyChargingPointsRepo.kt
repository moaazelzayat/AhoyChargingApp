package com.ahoy.ahoychargingapp.repository

import com.ahoy.ahoychargingapp.data.ChargingPointsResponse
import com.ahoy.ahoychargingapp.service.chargingpoints.ChargingPointsService
import javax.inject.Inject

class AhoyChargingPointsRepo @Inject constructor(
    private val service: ChargingPointsService
): ChargingPointsRepo{
    override suspend fun getChargingPoints(lat: Double, lng: Double): ChargingPointsResponse? =
        service.getPointsList(latitude = lat, longitude = lng)
}