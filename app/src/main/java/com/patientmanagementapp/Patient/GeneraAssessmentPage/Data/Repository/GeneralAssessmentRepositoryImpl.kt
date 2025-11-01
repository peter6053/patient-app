package com.patientmanagementapp.Patient.GeneraAssessmentPage.Data.Repository

import com.patientmanagementapp.Patient.GeneraAssessmentPage.Data.Remote.GeneralAssessmentApi
import com.patientmanagementapp.Patient.GeneraAssessmentPage.Dormain.Repository.GeneralAssessmentRepository
import com.patientmanagementapp.Patient.GeneraAssessmentPage.Dormain.models.AddVisitRequest
import com.patientmanagementapp.Patient.GeneraAssessmentPage.Dormain.models.AddVisitResponse
import com.patientmanagementapp.Utils.Resource
import javax.inject.Inject

class GeneralAssessmentRepositoryImpl @Inject constructor(
    private val api: GeneralAssessmentApi
) : GeneralAssessmentRepository {



    override suspend fun submitAssessment(request: AddVisitRequest): Resource<AddVisitResponse> {
        return try {
            val response = api.submitGeneralAssessment(request)
            Resource.Success(response)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Unknown error")
        }    }
}