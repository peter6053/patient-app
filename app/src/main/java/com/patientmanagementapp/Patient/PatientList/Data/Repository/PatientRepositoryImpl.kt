package com.patientmanagementapp.Patient.PatientList.Data.Repository

import com.patientmanagementapp.Patient.PatientList.Data.Remote.PatientApi
import com.patientmanagementapp.Patient.PatientList.Dormain.Model.PatientListResponse
import com.patientmanagementapp.Patient.PatientList.repository.PatientRepository
import javax.inject.Inject

class PatientRepositoryImpl @Inject constructor(
    private val api: PatientApi
) : PatientRepository {

    override suspend fun getPatients(): PatientListResponse {
        return api.getPatients()
    }
}