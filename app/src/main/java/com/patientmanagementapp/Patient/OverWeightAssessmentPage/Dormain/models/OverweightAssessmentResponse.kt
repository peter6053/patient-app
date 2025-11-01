package com.patientmanagementapp.Patient.OverWeightAssessmentPage.Dormain.models

data class OverWeightAssessmentResponse(
    val code: Int,
    val `data`: Data,
    val message: String,
    val success: Boolean
) {
    data class Data(
        val message: String,
        val slug: Int
    )
}