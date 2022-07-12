package com.ahoy.ahoychargingapp.repository

import com.ahoy.ahoychargingapp.data.ChargingPointsResponseItem
import com.ahoy.ahoychargingapp.data.local.dao.SpotDao
import com.ahoy.ahoychargingapp.data.local.model.SpotModel
import com.ahoy.ahoychargingapp.data.ui.ChargingPointModel
import com.ahoy.ahoychargingapp.service.chargingpoints.ChargingPointsService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class AhoyChargingPointsRepo @Inject constructor(
    private val service: ChargingPointsService,
    private val spotDao: SpotDao
): ChargingPointsRepo{

    private val coroutineContext: CoroutineContext
        get() = Dispatchers.IO

    override suspend fun getChargingPoints(lat: Double, lng: Double): List<ChargingPointModel>? {
        val cachedData =  withContext(coroutineContext){
            spotDao.getAll()
        }
        if (cachedData.isNotEmpty()) return cachedData.mapToUiModel()

        val remoteDate = service.getPointsList(latitude = lat, longitude = lng)
        withContext(coroutineContext){
            spotDao.insertAll(remoteDate.mapToLocalData())
        }

        return remoteDate.mapToUi()
    }

    private fun List<ChargingPointsResponseItem>.mapToLocalData(): List<SpotModel> = map {
        SpotModel(title = it.AddressInfo.Title, uuid = it.UUID, isRecentlyVerified = it.IsRecentlyVerified)
    }

    private fun List<SpotModel>.mapToUiModel(): List<ChargingPointModel> = map {
        ChargingPointModel(title = it.title, uuid = it.uuid, isRecentlyVerified = it.isRecentlyVerified)
    }

    private fun List<ChargingPointsResponseItem>.mapToUi(): List<ChargingPointModel> = map {
        ChargingPointModel(title = it.AddressInfo.Title, uuid = it.UUID, isRecentlyVerified = it.IsRecentlyVerified)
    }

}