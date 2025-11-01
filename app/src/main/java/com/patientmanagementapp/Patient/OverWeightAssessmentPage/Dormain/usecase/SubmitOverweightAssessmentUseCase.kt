package com.patientmanagementapp.Patient.OverWeightAssessmentPage.Dormain.usecase

import com.patientmanagementapp.Patient.OverWeightAssessmentPage.Dormain.Repository.OverweightAssessmentRepository
import com.patientmanagementapp.Patient.OverWeightAssessmentPage.Dormain.models.OverweightAssessmentRequest
import com.patientmanagementapp.Patient.OverWeightAssessmentPage.Dormain.models.OverWeightAssessmentResponse
import com.patientmanagementapp.Utils.Resource


class SubmitOverweightAssessmentUseCase(
    private val repository: OverweightAssessmentRepository
) {
    suspend operator fun invoke(request: OverweightAssessmentRequest): Resource<OverWeightAssessmentResponse> {
        return repository.submitAssessment(request)
    }
}