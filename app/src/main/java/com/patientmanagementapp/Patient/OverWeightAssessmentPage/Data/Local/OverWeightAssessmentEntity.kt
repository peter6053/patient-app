package com.patientmanagementapp.Patient.OverWeightAssessmentPage.Data.Local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "general_assessments")
data class OverWeightAssessmentEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val patientId: String,
    val patientName: String, // NEW
    val visitDate: String,
    val generalHealth: String,
    val onDiet: String,
    val comments: String
)
