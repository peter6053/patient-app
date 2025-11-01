package com.patientmanagementapp.Patient.Vitals.Dormain

interface VitalsRepository {
    suspend fun submitVitals(request: VitalsRequestBody): VitalsResonseBody
}