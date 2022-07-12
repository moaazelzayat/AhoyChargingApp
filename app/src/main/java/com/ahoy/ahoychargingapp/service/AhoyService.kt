package com.ahoy.ahoychargingapp.service

import com.ahoy.ahoychargingapp.data.ChargingPointsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface AhoyService {

    @GET("poi")
    fun getPOIList(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("maxresults") maxResults: Int
    ): Call<ChargingPointsResponse>
}