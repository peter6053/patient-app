package com.patientmanagementapp.Patient.OverWeightAssessmentPage.Data.Local.Dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.patientmanagementapp.Patient.OverWeightAssessmentPage.Data.Local.OverWeightAssessmentEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface OverWeightAssessmentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(overweight: OverWeightAssessmentEntity)

    @Query("SELECT * FROM overweight_assessments WHERE patientId = :patientId")
    suspend fun getByPatientId(patientId: String): List<OverWeightAssessmentEntity>

    @Query("SELECT * FROM overweight_assessments")
    fun getAll(): Flow<List<OverWeightAssessmentEntity>>

    @Delete
    suspend fun delete(overweight: OverWeightAssessmentEntity)

    @Query("DELETE FROM overweight_assessments")
    suspend fun deleteAll()
}
