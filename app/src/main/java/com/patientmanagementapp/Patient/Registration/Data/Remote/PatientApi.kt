package com.patientmanagementapp.PatientRegistration.data.remote

import com.patientmanagementapp.Patient.Registration.Dormain.Models.RegisterPatientRequest
import com.patientmanagementapp.Patient.Registration.Dormain.Models.RegisterPatientResponse

import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface PatientApi {

    @POST("patients/register")
    suspend fun registerPatient(
       // @Header("Authorization") token: String,
        @Body patient: RegisterPatientRequest
    ): RegisterPatientResponse
}