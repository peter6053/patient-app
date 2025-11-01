package com.patientmanagementapp.Patient.GeneraAssessmentPage.Data.Local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "general_assessments")
data class GeneralAssessmentEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val patientId: String,
    val patientName: String, // NEW
    val visitDate: String,
    val generalHealth: String,
    val onDiet: String,
    val comments: String
)
