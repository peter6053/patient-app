package com.patientmanagementapp.Patient.OverWeightAssessmentPage.Data.Repository

import com.patientmanagementapp.Patient.OverWeightAssessmentPage.Data.Remote.OverWeightAssessmentApi
import com.patientmanagementapp.Patient.OverWeightAssessmentPage.Dormain.Repository.OverweightAssessmentRepository
import com.patientmanagementapp.Patient.OverWeightAssessmentPage.Dormain.models.OverWeightAssessmentResponse
import com.patientmanagementapp.Patient.OverWeightAssessmentPage.Dormain.models.OverweightAssessmentRequest
import com.patientmanagementapp.Utils.Resource


import javax.inject.Inject

class OverWeightAssessmentRepositoryImpl @Inject constructor(
    private val api: OverWeightAssessmentApi
) : OverweightAssessmentRepository {

    override suspend fun submitAssessment(request: OverweightAssessmentRequest): Resource<OverWeightAssessmentResponse> {
        return try {
            val response = api.submitOverweightAssessment(request)
            Resource.Success(response)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Unknown error")
        }
    }
}


