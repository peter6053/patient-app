package com.patientmanagementapp.Patient.GeneraAssessmentPage.Dormain.models

data class AddVisitResponse(
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