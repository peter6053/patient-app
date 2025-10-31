package com.patientmanagementapp.Patient.Registration.Dormain.Models

data class RegisterPatientRequest(
    val dob: String,
    val firstname: String,
    val gender: String,
    val lastname: String,
    val reg_date: String,
    val unique: String
)