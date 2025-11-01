package com.patientmanagementapp.Patient.Registration.Data.Local.Entitity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "patients")
data class RegisterPatientEntity(
    @PrimaryKey
    val id: String,
    val dob: String,
    val firstname: String,
    val gender: String,
    val lastname: String,
    val reg_date: String,
    val unique: String
)