package com.patientmanagementapp.Auth.Di.NetworkModule

import com.patientmanagementapp.Utils.DataStoreManager
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val dataStoreManager: DataStoreManager
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val token = runBlocking { dataStoreManager.accessTokenFlow.first() }
        val request = chain.request().newBuilder()
            .addHeader("Authorization", "Token $token")
            .build()
        return chain.proceed(request)
    }
}
