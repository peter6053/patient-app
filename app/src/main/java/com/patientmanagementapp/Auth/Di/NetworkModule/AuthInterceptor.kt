package com.patientmanagementapp.Auth.Di.NetworkModule

import com.patientmanagementapp.Utils.DataStoreManager
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val dataStoreManager: DataStoreManager
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val token = runBlocking { dataStoreManager.accessTokenFlow.firstOrNull() }

        val requestBuilder = originalRequest.newBuilder()
            .header("Content-Type", "application/json")

        if (!token.isNullOrEmpty()) {
            // Use the correct Bearer format
            requestBuilder.header("Authorization", "Bearer $token")
        }

        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}

