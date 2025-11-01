package com.patientmanagementapp.Patient.OverWeightAssessmentPage.Dormain.Repository





import com.patientmanagementapp.Patient.OverWeightAssessmentPage.Dormain.models.OverweightAssessmentRequest
import com.patientmanagementapp.Patient.OverWeightAssessmentPage.Dormain.models.OverWeightAssessmentResponse
import com.patientmanagementapp.Utils.Resource

interface OverweightAssessmentRepository {
    suspend fun submitAssessment(request: OverweightAssessmentRequest): Resource<OverWeightAssessmentResponse>
}

