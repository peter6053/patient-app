package com.patientmanagementapp.Auth.Dormain.Repository

import com.patientmanagementapp.Auth.Dormain.Models.LoginRequest
import com.patientmanagementapp.Auth.Dormain.Models.LoginResponse
import com.patientmanagementapp.Auth.Dormain.Models.SignUpRequest
import com.patientmanagementapp.Auth.Dormain.Models.SignupResponse
import com.patientmanagementapp.Utils.Resource

interface AuthRepository {
    suspend fun login(request: LoginRequest): LoginResponse
    suspend fun signup(request: SignUpRequest): Resource<SignupResponse>
}