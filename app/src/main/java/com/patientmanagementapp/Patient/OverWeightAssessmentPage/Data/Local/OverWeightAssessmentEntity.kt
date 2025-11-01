package com.patientmanagementapp.Patient.OverWeightAssessmentPage.Data.Local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "overweight_assessments")
data class OverWeightAssessmentEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val patientId: String,
    val patientName: String,
    val visitDate: String,
    val generalHealth: String,
    val onDiet: String,
    val comments: String
)
