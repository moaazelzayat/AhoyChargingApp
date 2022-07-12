package com.ahoy.ahoychargingapp.di

import com.ahoy.ahoychargingapp.service.chargingpoints.AhoyChargingPointsService
import com.ahoy.ahoychargingapp.service.chargingpoints.ChargingPointsService
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ServiceModule {

    @Binds
    abstract fun bindChargingPointsService(
        service: AhoyChargingPointsService
    ): ChargingPointsService

}