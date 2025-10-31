package com.patientmanagementapp.Patient.Registration.Data.Repository


import com.patientmanagementapp.Patient.Registration.Dormain.Models.RegisterPatientRequest
import com.patientmanagementapp.Patient.Registration.Dormain.Models.RegisterPatientResponse
import com.patientmanagementapp.Patient.Registration.Repository.PatientRepository
import com.patientmanagementapp.PatientRegistration.data.remote.PatientApi
import com.patientmanagementapp.Utils.DataStoreManager
import kotlinx.coroutines.flow.firstOrNull

import javax.inject.Inject

class PatientRepositoryImpl @Inject constructor(
    private val api: PatientApi,
    private val dataStoreManager: DataStoreManager

) : PatientRepository {



    override suspend fun registerPatient(request: RegisterPatientRequest): RegisterPatientResponse {

        val token = dataStoreManager.accessTokenFlow.firstOrNull() ?: ""
        val authHeader = "Bearer $token"
        return api.registerPatient( request)
    }
}