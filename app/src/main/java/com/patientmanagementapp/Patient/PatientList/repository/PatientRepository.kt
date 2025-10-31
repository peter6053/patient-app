package com.patientmanagementapp.Patient.PatientList.repository

import com.patientmanagementapp.Patient.PatientList.Dormain.Model.PatientListResponse


interface PatientRepository {
    suspend fun getPatients(): PatientListResponse
}