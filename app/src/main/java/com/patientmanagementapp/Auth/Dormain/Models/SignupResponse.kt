package com.patientmanagementapp.Auth.Dormain.Models

data class SignupResponse(
    val code: Int,
    val `data`: Data,
    val message: String,
    val success: Boolean
) {
    data class Data(
        val message: String,
        val proceed: Int
    )
}