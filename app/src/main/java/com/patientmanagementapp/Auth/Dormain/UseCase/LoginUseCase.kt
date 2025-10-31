package com.patientmanagementapp.Auth.Dormain.UseCase

import com.patientmanagementapp.Auth.Dormain.Models.LoginRequest
import com.patientmanagementapp.Auth.Dormain.Models.LoginResponse
import com.patientmanagementapp.Auth.Dormain.Repository.AuthRepository
import com.patientmanagementapp.Utils.Resource
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String): Resource<LoginResponse> {
        return try {
            val response = repository.login(LoginRequest(email, password))
            Resource.Success(response)
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Unexpected error")
        }
    }
}
