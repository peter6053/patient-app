package com.patientmanagementapp.Auth.Dormain.UseCase

import com.patientmanagementapp.Auth.Dormain.Models.SignUpRequest
import com.patientmanagementapp.Auth.Dormain.Models.SignupResponse
import com.patientmanagementapp.Auth.Dormain.Repository.AuthRepository
import com.patientmanagementapp.Utils.Resource
import javax.inject.Inject

class SignupUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(
        firstname: String,
        lastname: String,
        email: String,
        password: String
    ): Resource<SignupResponse> {
        return repository.signup(SignUpRequest(email, firstname, lastname, password))
    }
}

