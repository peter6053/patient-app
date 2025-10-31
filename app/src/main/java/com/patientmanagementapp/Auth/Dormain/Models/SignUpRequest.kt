package com.patientmanagementapp.Auth.Dormain.Models

data class SignUpRequest(
    val email: String,
    val firstname: String,
    val lastname: String,
    val password: String
)