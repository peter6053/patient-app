package com.patientmanagementapp.Patient.Vitals.Data.Local.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vitals")
data class VitalsEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val bmi: String,
    val height: String,
    val patient_id: String,
    val visit_date: String,
    val weight: String
)
