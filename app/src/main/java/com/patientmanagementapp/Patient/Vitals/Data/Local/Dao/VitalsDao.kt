package com.patientmanagementapp.Patient.Vitals.Data.Local.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.patientmanagementapp.Patient.Vitals.Data.Local.Entity.VitalsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface VitalsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVitals(vitals: VitalsEntity)

    @Query("SELECT * FROM vitals WHERE patient_id = :patientId ORDER BY visit_date DESC")
    fun getVitalsByPatient(patientId: String): Flow<List<VitalsEntity>>

    @Query("SELECT * FROM vitals ORDER BY visit_date DESC")
    suspend fun getAllVitals(): List<VitalsEntity>
}