package com.ahoy.ahoychargingapp.service.chargingpoints

import com.ahoy.ahoychargingapp.data.ChargingPointsResponse

/**
 * This interface should encapsulate all relative [ChargingPointsResponse] related calls, etc.
 */
interface ChargingPointsService {

    /**
     * Returns [ChargingPointsResponse] depend on current location to user.
     */
    suspend fun getPointsList(latitude: Double, longitude: Double) : ChargingPointsResponse

}