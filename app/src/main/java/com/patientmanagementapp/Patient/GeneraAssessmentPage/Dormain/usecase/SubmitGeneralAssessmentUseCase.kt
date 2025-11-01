package com.patientmanagementapp.Patient.GeneraAssessmentPage.Dormain.usecase

import com.patientmanagementapp.Patient.GeneraAssessmentPage.Dormain.Repository.GeneralAssessmentRepository
import com.patientmanagementapp.Patient.GeneraAssessmentPage.Dormain.models.AddVisitRequest
import com.patientmanagementapp.Patient.GeneraAssessmentPage.Dormain.models.AddVisitResponse
import com.patientmanagementapp.Utils.Resource

class SubmitGeneralAssessmentUseCase(
    private val repository: GeneralAssessmentRepository
) {
    suspend operator fun invoke(request: AddVisitRequest): Resource<AddVisitResponse> {
        return repository.submitAssessment(request)
    }
}