package com.patientmanagementapp.Patient.PatientList.Dormain.Model

data class PatientListResponse(
    val code: Int,
    val `data`: List<Data>,
    val message: String,
    val success: Boolean
) {
    data class Data(
        val created_at: String,
        val dob: String,
        val firstname: String,
        val gender: String,
        val id: Int,
        val lastname: String,
        val reg_date: String,
        val unique: String,
        val updated_at: String
    )
}