package com.patientmanagementapp.Patient.GeneraAssessmentPage.Data.Local

data class GeneralAssessmentRequest(
    val patientId: String,
    val patientName: String, // NEW
    val visitDate: String,
    val generalHealth: String,
    val onDiet: String,
    val comments: String
)