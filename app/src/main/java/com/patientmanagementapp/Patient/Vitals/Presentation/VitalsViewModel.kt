package com.patientmanagementapp.Vitals.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.patientmanagementapp.Navigation.Screen
import com.patientmanagementapp.Patient.Vitals.Data.Local.Dao.VitalsDao
import com.patientmanagementapp.Patient.Vitals.Data.Local.Entity.VitalsEntity
import com.patientmanagementapp.Patient.Vitals.Dormain.Usecases.SubmitVitalsUseCase
import com.patientmanagementapp.Patient.Vitals.Dormain.VitalsRequestBody
import com.patientmanagementapp.Patient.Vitals.Dormain.VitalsResonseBody

import com.patientmanagementapp.Utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VitalsViewModel @Inject constructor(
    private val submitVitalsUseCase: SubmitVitalsUseCase,
    private val vitalsDao: VitalsDao
) : ViewModel() {

    private val _height = MutableStateFlow("")
    val height = _height.asStateFlow()

    private val _weight = MutableStateFlow("")
    val weight = _weight.asStateFlow()

    private val _bmi = MutableStateFlow("")
    val bmi = _bmi.asStateFlow()

    private val _visitDate = MutableStateFlow("")
    val visitDate = _visitDate.asStateFlow()

    private val _state = MutableStateFlow<Resource<VitalsResonseBody>>(Resource.Idle())
    val state = _state.asStateFlow()

    private val _navigateTo = MutableSharedFlow<String>()
    val navigateTo = _navigateTo.asSharedFlow()

    fun onHeightChanged(value: String) {
        _height.value = value
        calculateBmi()
    }

    fun onWeightChanged(value: String) {
        _weight.value = value
        calculateBmi()
    }

    fun onVisitDateChanged(value: String) {
        _visitDate.value = value
    }

    private fun calculateBmi() {
        val h = _height.value.toFloatOrNull() ?: 0f
        val w = _weight.value.toFloatOrNull() ?: 0f
        if (h > 0f && w > 0f) {
            val hMeters = h / 100f
            _bmi.value = String.format("%.2f", w / (hMeters * hMeters))
        } else {
            _bmi.value = ""
        }
    }

    private fun validateInput(): String? {
        return when {
            _visitDate.value.isBlank() -> "Please select a visit date."
            _height.value.isBlank() -> "Please enter height."
            _weight.value.isBlank() -> "Please enter weight."
            _bmi.value.isBlank() -> "BMI could not be calculated. Check your inputs."
            else -> null
        }
    }

    fun submitVitals(patientId: String) {
        viewModelScope.launch {
            val validationError = validateInput()
            if (validationError != null) {
                _state.value = Resource.Error(validationError)
                return@launch
            }

            _state.value = Resource.Loading()
            try {
                val request = VitalsRequestBody(
                    patient_id = patientId,
                    height = _height.value,
                    weight = _weight.value,
                    bmi = _bmi.value,
                    visit_date = _visitDate.value
                )

                val response = submitVitalsUseCase(request)
                _state.value = response

                val vitalsEntity = VitalsEntity(

                    patient_id = patientId,
                    height = _height.value,
                    weight = _weight.value,
                    bmi = _bmi.value,
                    visit_date = _visitDate.value,
                )
                vitalsDao.insertVitals(vitalsEntity)

                if (response is Resource.Success) {
                    val bmiValue = _bmi.value.toFloatOrNull() ?: 0f
                    val vitalId = response.data?.data?.id?.toString() ?: vitalsEntity.id.toString()
                    val patientname = response.data?.data?.id?.toString() ?: vitalsEntity.id.toString()

                    val targetScreen = if (bmiValue <= 25f) {
                        Screen.GeneralAssessment.createRoute(patientId, vitalId,patientname)
                    } else {
                        Screen.OverweightAssessment.createRoute(patientId, vitalId, patientName = patientname)
                    }

                    _navigateTo.emit(targetScreen)
                }

            } catch (e: Exception) {
                _state.value = Resource.Error(e.message ?: "Unknown error occurred")
            }
        }
    }
}
