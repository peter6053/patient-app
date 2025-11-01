package com.patientmanagementapp.Patient.GeneraAssessmentPage.Data.Local.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.patientmanagementapp.Patient.GeneraAssessmentPage.Data.Local.GeneralAssessmentEntity

@Dao
interface GeneralAssessmentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAssessment(assessment: GeneralAssessmentEntity)

    @Query("SELECT * FROM general_assessments WHERE patientId = :patientId ORDER BY visitDate DESC")
    suspend fun getAssessmentsByPatient(patientId: String): List<GeneralAssessmentEntity>

    @Query("DELETE FROM general_assessments WHERE id = :id")
    suspend fun deleteAssessment(id: Int)
}
