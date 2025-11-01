package com.patientmanagementapp.Patient.Vitals.Dormain

data class VitalsRequestBody(
    val bmi: String,
    val height: String,
    val patient_id: String,
    val visit_date: String,
    val weight: String
)