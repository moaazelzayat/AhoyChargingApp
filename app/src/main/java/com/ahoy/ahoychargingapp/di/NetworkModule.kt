package com.ahoy.ahoychargingapp.di

import com.ahoy.ahoychargingapp.BuildConfig
import com.ahoy.ahoychargingapp.service.AhoyService
import com.ahoy.ahoychargingapp.service.interceptor.AhoyInterceptor
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import okhttp3.Authenticator
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val DEFAULT_TIMEOUT = 15L

    @Provides
    @Singleton
    fun ahoyInterceptor(
        interceptor: AhoyInterceptor
    ): Interceptor = interceptor

    @Provides
    @Singleton
    fun okhttp(
        interceptor: Interceptor
    ): OkHttpClient =
        OkHttpClient.Builder()
            .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .build()

    @Provides
    @Singleton
    fun moshi(): Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    @Provides
    @Singleton
    fun retrofit(
        okHttpClient: OkHttpClient
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create())
            .addConverterFactory(ScalarsConverterFactory.create())
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun service(
        retrofit: Retrofit
    ): AhoyService = retrofit.create(AhoyService::class.java)
}