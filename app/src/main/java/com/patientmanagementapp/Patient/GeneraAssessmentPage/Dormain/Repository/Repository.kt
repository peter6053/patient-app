package com.patientmanagementapp.Patient.GeneraAssessmentPage.Dormain.Repository


import com.patientmanagementapp.Patient.GeneraAssessmentPage.Dormain.models.AddVisitRequest
import com.patientmanagementapp.Patient.GeneraAssessmentPage.Dormain.models.AddVisitResponse
import com.patientmanagementapp.Utils.Resource

interface GeneralAssessmentRepository {
    suspend fun submitAssessment(request: AddVisitRequest): Resource<AddVisitResponse>
}