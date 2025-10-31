package com.patientmanagementapp.Patient.Registration.Dormain.Models

data class RegisterPatientResponse(
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