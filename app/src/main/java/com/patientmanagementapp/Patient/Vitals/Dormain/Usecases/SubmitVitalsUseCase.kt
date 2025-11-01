package com.patientmanagementapp.Patient.Vitals.Dormain.Usecases

import com.patientmanagementapp.Patient.Vitals.Dormain.VitalsRepository
import com.patientmanagementapp.Patient.Vitals.Dormain.VitalsRequestBody
import com.patientmanagementapp.Patient.Vitals.Dormain.VitalsResonseBody
import com.patientmanagementapp.Utils.Resource
import javax.inject.Inject

class SubmitVitalsUseCase @Inject constructor(
    private val repository: VitalsRepository
) {
    suspend operator fun invoke(request: VitalsRequestBody): Resource<VitalsResonseBody> {
        return try {
            val response = repository.submitVitals(request)
            Resource.Success(response)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Unknown error")
        }
    }
}
