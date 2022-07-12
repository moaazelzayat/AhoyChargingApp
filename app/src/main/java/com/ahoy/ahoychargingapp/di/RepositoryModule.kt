package com.ahoy.ahoychargingapp.di

import com.ahoy.ahoychargingapp.repository.AhoyChargingPointsRepo
import com.ahoy.ahoychargingapp.repository.ChargingPointsRepo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindChargingPointsRepo(
        repository: AhoyChargingPointsRepo
    ): ChargingPointsRepo

}