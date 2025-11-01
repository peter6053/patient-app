package com.patientmanagementapp.Patient.Vitals.Data.Local.Dao

import com.patientmanagementapp.Patient.Vitals.Data.Local.Dao.VitalsDao
import com.patientmanagementapp.Patient.Vitals.Data.Local.Entity.VitalsEntity
import javax.inject.Inject

class LocalVitalsRepository @Inject constructor(
    private val dao: VitalsDao
) {
    suspend fun saveVitals(vitals: VitalsEntity) {
        dao.insertVitals(vitals)
    }

    suspend fun getVitalsByPatient(patientId: String) = dao.getVitalsByPatient(patientId)
}