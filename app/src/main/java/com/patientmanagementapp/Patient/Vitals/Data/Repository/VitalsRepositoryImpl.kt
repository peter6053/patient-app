package com.patientmanagementapp.Patient.Vitals.Data.Repository

import com.patientmanagementapp.Patient.Vitals.Data.Remote.VitalsApi
import com.patientmanagementapp.Patient.Vitals.Dormain.VitalsRepository
import com.patientmanagementapp.Patient.Vitals.Dormain.VitalsRequestBody
import com.patientmanagementapp.Patient.Vitals.Dormain.VitalsResonseBody
import com.patientmanagementapp.Utils.DataStoreManager
import javax.inject.Inject

class VitalsRepositoryImpl @Inject constructor(
    private val api: VitalsApi,
) : VitalsRepository {
    override suspend fun submitVitals(request: VitalsRequestBody): VitalsResonseBody {
        return api.submitVitals( request)
    }
}
