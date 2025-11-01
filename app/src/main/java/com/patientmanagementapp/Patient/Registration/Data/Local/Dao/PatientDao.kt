package com.patientmanagementapp.Patient.Registration.Data.Local.Dao


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.patientmanagementapp.Patient.Registration.Data.Local.Entitity.RegisterPatientEntity

@Dao
interface PatientDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPatient(patient: RegisterPatientEntity)

    @Query("SELECT * FROM patients")
    suspend fun getAllPatients(): List<RegisterPatientEntity>

    @Query("DELETE FROM patients")
    suspend fun clearAllPatients()

    @Query("""
        SELECT p.id, p.firstname, p.lastname, p.dob, v.bmi
        FROM patients AS p
        LEFT JOIN (
            SELECT patient_id, bmi
            FROM vitals
            WHERE visit_date = (
                SELECT MAX(visit_date) 
                FROM vitals 
                WHERE patient_id = vitals.patient_id
            )
        ) AS v
        ON p.id = v.patient_id
        ORDER BY p.firstname
    """)
    suspend fun getPatientsWithLatestBmi(): List<PatientWithBmi>
}


data class PatientWithBmi(
    val id: String,
    val firstname: String,
    val lastname: String,
    val dob: String,
    val bmi: Float?
)
