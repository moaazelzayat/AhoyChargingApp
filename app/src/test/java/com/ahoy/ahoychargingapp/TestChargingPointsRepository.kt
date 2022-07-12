package com.ahoy.ahoychargingapp

import com.ahoy.ahoychargingapp.data.ChargingPointsResponse
import com.ahoy.ahoychargingapp.repository.AhoyChargingPointsRepo
import com.ahoy.ahoychargingapp.repository.ChargingPointsRepo
import com.ahoy.ahoychargingapp.service.chargingpoints.ChargingPointsService
import io.mockk.*
import io.mockk.impl.annotations.RelaxedMockK
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

class TestChargingPointsRepository {

    private var sut: ChargingPointsRepo? = null

    @RelaxedMockK
    lateinit var mockChargingPointsService: ChargingPointsService
    private val testLat = 30.5465
    private val testLng = 30.56954

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        sut = AhoyChargingPointsRepo(
            service = mockChargingPointsService,
        )
    }

    @After
    fun teardown() {
        sut = null
    }

    @Test
    fun testGetAccountInfo() {
        val mockChargingPointsResponse = mockk<ChargingPointsResponse>()
        coEvery { mockChargingPointsService.getPointsList(testLat, testLng) } returns mockChargingPointsResponse

        val result = runBlocking { sut?.getChargingPoints(testLat, testLng) }

        coVerify { mockChargingPointsService.getPointsList(testLat, testLng) }
        confirmVerified(mockChargingPointsService)

        assertEquals(mockChargingPointsResponse, result)
    }

}