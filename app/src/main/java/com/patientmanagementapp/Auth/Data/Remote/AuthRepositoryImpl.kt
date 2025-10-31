package com.patientmanagementapp.Auth.Data.Remote

import com.patientmanagementapp.Auth.AuthApi.AuthApi
import com.patientmanagementapp.Auth.Dormain.Models.LoginRequest
import com.patientmanagementapp.Auth.Dormain.Models.LoginResponse
import com.patientmanagementapp.Auth.Dormain.Models.SignUpRequest
import com.patientmanagementapp.Auth.Dormain.Models.SignupResponse
import com.patientmanagementapp.Auth.Dormain.Repository.AuthRepository
import com.patientmanagementapp.Utils.Resource
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val api: AuthApi
) : AuthRepository {
    override suspend fun login(request: LoginRequest): LoginResponse {
        return api.login(request)
    }
    override suspend fun signup(request: SignUpRequest): Resource<SignupResponse> {
        return try {
            val response = api.signUp(request)
            Resource.Success(response)
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "An unexpected error occurred")
        }
    }
}