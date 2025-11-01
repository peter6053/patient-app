package com.patientmanagementapp.Patient.Vitals.Data.Remote

import com.patientmanagementapp.Patient.Vitals.Dormain.VitalsRequestBody
import com.patientmanagementapp.Patient.Vitals.Dormain.VitalsResonseBody
import retrofit2.http.Body
import retrofit2.http.POST

interface VitalsApi {
    @POST("vital/add")
    suspend fun submitVitals(
        @Body request: VitalsRequestBody
    ): VitalsResonseBody
}
