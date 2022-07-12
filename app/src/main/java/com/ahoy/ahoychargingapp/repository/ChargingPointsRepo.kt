package com.ahoy.ahoychargingapp.repository

import com.ahoy.ahoychargingapp.data.ChargingPointsResponse

/**
 * Repository layer associated with handling all information regarding charging points,
 * and mange caching
 */
interface ChargingPointsRepo {

    /**
     * Get charging points
     */
    suspend fun getChargingPoints(lat: Double, lng: Double) :
            ChargingPointsResponse?

}