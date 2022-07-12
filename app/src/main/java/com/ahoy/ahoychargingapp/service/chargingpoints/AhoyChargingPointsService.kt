package com.ahoy.ahoychargingapp.service.chargingpoints

import com.ahoy.ahoychargingapp.data.ChargingPointsResponse
import com.ahoy.ahoychargingapp.service.AhoyService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class AhoyChargingPointsService @Inject constructor(
    private val service: AhoyService
): ChargingPointsService{
    override suspend fun getPointsList(
        latitude: Double,
        longitude: Double
    ): ChargingPointsResponse = suspendCoroutine { continuation ->
        service.getPOIList(latitude = latitude, longitude = longitude, maxResults = 20).enqueue(object : Callback<ChargingPointsResponse>{
            override fun onResponse(
                call: Call<ChargingPointsResponse>,
                response: Response<ChargingPointsResponse>
            ) {
                continuation.resume(response.body()!!)
            }

            override fun onFailure(call: Call<ChargingPointsResponse>, t: Throwable) {
                continuation.resumeWithException(t)
            }
        })
    }
}