package com.patientmanagementapp.Auth.Dormain.Models

data class LoginResponse(
    val code: Int,
    val `data`: Data,
    val message: String,
    val success: Boolean
) {
    data class Data(
        val access_token: String,
        val created_at: String,
        val email: String,
        val id: Int,
        val name: String,
        val updated_at: String
    )
}