package com.patientmanagementapp.Patient.GeneraAssessmentPage.Data.Remote

import com.patientmanagementapp.Patient.GeneraAssessmentPage.Dormain.models.AddVisitRequest
import com.patientmanagementapp.Patient.GeneraAssessmentPage.Dormain.models.AddVisitResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface GeneralAssessmentApi {
    @POST("visits/add")
    suspend fun submitGeneralAssessment(
        @Body request: AddVisitRequest
    ): AddVisitResponse
}