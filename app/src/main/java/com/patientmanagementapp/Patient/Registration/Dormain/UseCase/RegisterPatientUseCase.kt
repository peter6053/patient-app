package com.patientmanagementapp.Patient.Registration.Dormain.UseCase



import com.patientmanagementapp.Patient.Registration.Dormain.Models.RegisterPatientRequest
import com.patientmanagementapp.Patient.Registration.Dormain.Models.RegisterPatientResponse
import com.patientmanagementapp.Patient.Registration.Repository.PatientRepository
import com.patientmanagementapp.Utils.Resource
import javax.inject.Inject

class RegisterPatientUseCase @Inject constructor(
    private val repository: PatientRepository
) {
    suspend operator fun invoke(request: RegisterPatientRequest): Resource<RegisterPatientResponse> {
        return try {
            val response = repository.registerPatient(request)
            Resource.Success(response)
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Unexpected error")
        }
    }
}