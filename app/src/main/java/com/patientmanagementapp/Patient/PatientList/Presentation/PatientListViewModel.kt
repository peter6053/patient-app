package com.patientmanagementapp.Patient.PatientList.Presentation


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.patientmanagementapp.Patient.PatientList.Dormain.Model.PatientListResponse
import com.patientmanagementapp.Patient.PatientList.Dormain.UseCases.GetPatientsUseCase
import com.patientmanagementapp.Patient.Registration.Data.Local.Dao.PatientDao
import com.patientmanagementapp.Patient.Registration.Data.Local.Dao.PatientWithBmi
import com.patientmanagementapp.Patient.Registration.Data.Local.Entitity.RegisterPatientEntity
import com.patientmanagementapp.Patient.Vitals.Data.Local.Dao.VitalsDao
import com.patientmanagementapp.Patient.Vitals.Data.Local.Entity.VitalsEntity

import com.patientmanagementapp.Utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flatten
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject
import kotlin.collections.emptyList

@HiltViewModel
class PatientListViewModel @Inject constructor(
    private val patientDao: PatientDao,
    private val vitalsDao: VitalsDao,
    private val getPatientsUseCase: GetPatientsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<Resource<List<PatientWithBmi>>>(Resource.Idle())
    val state: StateFlow<Resource<List<PatientWithBmi>>> = _state.asStateFlow()

    fun loadPatients() {
        viewModelScope.launch {
            _state.value = Resource.Loading()
            try {
                val localPatients = patientDao.getAllPatients()
                val localWithBmi = localPatients.map { it.toPatientWithBmi(vitalsDao) }
                if (localWithBmi.isNotEmpty()) {
                    _state.value = Resource.Success(localWithBmi)
                }

                // ✅ Step 2: Try fetching from API
                val remoteResponse = getPatientsUseCase()
                val apiPatients = remoteResponse.data?.map { dto ->
                    RegisterPatientEntity(
                        id = dto.id.toString(),
                        dob = dto.dob,
                        firstname = dto.firstname,
                        gender = dto.gender,
                        lastname = dto.lastname,
                        reg_date = dto.reg_date,
                        unique = dto.unique
                    )
                } ?: emptyList()

                // ✅ Step 3: Update local DB
                apiPatients.forEach { patientDao.insertPatient(it) }

                // ✅ Step 4: Refresh local data
                val updatedPatients = patientDao.getAllPatients()
                val updatedWithBmi = updatedPatients.map { it.toPatientWithBmi(vitalsDao) }
                _state.value = Resource.Success(updatedWithBmi)

            } catch (e: Exception) {
                // ✅ Step 5: Fallback if offline or error
                val fallbackPatients = patientDao.getAllPatients()
                val fallbackWithBmi = fallbackPatients.map { it.toPatientWithBmi(vitalsDao) }
                if (fallbackWithBmi.isNotEmpty()) {
                    _state.value = Resource.Success(fallbackWithBmi)
                } else {
                    _state.value = Resource.Error("Offline mode: No cached data available.")
                }
            }
        }
    }
    fun filterPatientsByDate(selectedDate: LocalDate?) {
        viewModelScope.launch {
            _state.value = Resource.Loading()
            try {
                if (selectedDate == null) {
                    // No date selected → show all again
                    loadPatients()
                    return@launch
                }

                val allPatients = patientDao.getAllPatients()
                val allVitals = vitalsDao.getAllVitals()

                val filtered = allPatients.filter { patient ->
                    allVitals.any { vital ->
                        vital.patient_id == patient.unique &&
                                vital.visit_date.startsWith(selectedDate.toString())
                    }
                }

                val filteredWithBmi = filtered.map { it.toPatientWithBmi(vitalsDao) }

                if (filteredWithBmi.isNotEmpty()) {
                    _state.value = Resource.Success(filteredWithBmi)
                } else {
                    _state.value = Resource.Error("No records found for ${selectedDate}")
                }

            } catch (e: Exception) {
                _state.value = Resource.Error("Failed to filter by date: ${e.message}")
            }
        }
    }









    suspend fun getLocalPatients(): List<RegisterPatientEntity> = patientDao.getAllPatients()
    suspend fun getLocalVitals(): List<VitalsEntity> = vitalsDao.getAllVitals()
}



data class PatientWithBmi(
    val id: String,
    val firstname: String,
    val lastname: String,
    val dob: String,
    val bmiStatus: String
)
suspend fun RegisterPatientEntity.toPatientWithBmi(vitalsDao: VitalsDao): PatientWithBmi {
    val vitalsList = vitalsDao.getVitalsByPatient(id).firstOrNull() ?: emptyList()

    val latestVitals = vitalsList.firstOrNull()

    val bmi = latestVitals?.bmi?.toFloatOrNull()

    // Return object that matches your DAO data class
    return PatientWithBmi(
        id = id,
        firstname = firstname,
        lastname = lastname,
        dob = dob,
        bmi = bmi
    )
}




