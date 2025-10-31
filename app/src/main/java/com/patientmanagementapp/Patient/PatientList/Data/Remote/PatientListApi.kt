package com.patientmanagementapp.Patient.PatientList.Data.Remote


import com.patientmanagementapp.Patient.PatientList.Dormain.Model.PatientListResponse
import retrofit2.http.GET

interface PatientApi {
    @GET("patients/view")
    suspend fun getPatients(): PatientListResponse
}