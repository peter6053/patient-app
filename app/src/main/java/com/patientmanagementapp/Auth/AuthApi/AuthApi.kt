package com.patientmanagementapp.Auth.AuthApi

import com.patientmanagementapp.Auth.Dormain.Models.LoginRequest
import com.patientmanagementapp.Auth.Dormain.Models.LoginResponse
import com.patientmanagementapp.Auth.Dormain.Models.SignUpRequest
import com.patientmanagementapp.Auth.Dormain.Models.SignupResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("user/signin")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @POST("auth/signup")
    suspend fun signUp(@Body request: SignUpRequest): SignupResponse
}