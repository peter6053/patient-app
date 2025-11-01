package com.patientmanagementapp.Patient.OverWeightAssessmentPage.Data.Remote


import com.patientmanagementapp.Patient.OverWeightAssessmentPage.Dormain.models.OverWeightAssessmentResponse
import com.patientmanagementapp.Patient.OverWeightAssessmentPage.Dormain.models.OverweightAssessmentRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface OverWeightAssessmentApi {
    @POST("visits/add")
    suspend fun submitOverweightAssessment(
        @Body request: OverweightAssessmentRequest
    ): OverWeightAssessmentResponse

}


