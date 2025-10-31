package com.patientmanagementapp.Patient.Registration.Repository

import com.patientmanagementapp.Patient.Registration.Dormain.Models.RegisterPatientRequest
import com.patientmanagementapp.Patient.Registration.Dormain.Models.RegisterPatientResponse


interface PatientRepository {
    suspend fun registerPatient(request: RegisterPatientRequest): RegisterPatientResponse
}