package com.patientmanagementapp.Patient.Vitals.Dormain

data class VitalsResonseBody(
    val code: Int,
    val `data`: Data,
    val message: String,
    val success: Boolean
) {
    data class Data(
        val id: Int,
        val message: String,
        val patient_id: String,
        val slug: Int
    )
}