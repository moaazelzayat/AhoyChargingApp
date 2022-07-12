package com.ahoy.ahoychargingapp.service.interceptor

import com.ahoy.ahoychargingapp.BuildConfig
import kotlinx.coroutines.runBlocking
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.util.*
import javax.inject.Inject

class AhoyInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val requestBuilder: Request.Builder = original.newBuilder()
            .addHeader("X-API-Key", BuildConfig.API_KEY)

        val request = requestBuilder.method(original.method, original.body).build()
        return chain.proceed(request)
    }
}