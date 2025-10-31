package com.patientmanagementapp.Patient.PatientList.Dormain.UseCases


import com.patientmanagementapp.Patient.PatientList.Dormain.Model.PatientListResponse
import com.patientmanagementapp.Patient.PatientList.repository.PatientRepository
import javax.inject.Inject

class GetPatientsUseCase @Inject constructor(
    private val repository: PatientRepository
) {
    suspend operator fun invoke(): PatientListResponse {
        return repository.getPatients()
    }
}