package com.patientmanagementapp.Patient.GeneraAssessmentPage.Presentatin


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.patientmanagementapp.Navigation.Screen
import com.patientmanagementapp.Patient.GeneraAssessmentPage.Dormain.models.AddVisitRequest
import com.patientmanagementapp.Patient.GeneraAssessmentPage.Dormain.usecase.SubmitGeneralAssessmentUseCase

import com.patientmanagementapp.Utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GeneralAssessmentViewModel @Inject constructor(
    private val submitUseCase: SubmitGeneralAssessmentUseCase
) : ViewModel() {

    val visitDate = MutableStateFlow("")
    val generalHealth = MutableStateFlow("")
    val onDiet = MutableStateFlow("")
    val onDrugs = MutableStateFlow("")
    val comments = MutableStateFlow("")

    // 🔹 Validation error messages
    val visitDateError = MutableStateFlow<String?>(null)
    val generalHealthError = MutableStateFlow<String?>(null)
    val onDietError = MutableStateFlow<String?>(null)
    val onDrugsError = MutableStateFlow<String?>(null)
    val oncommebtError = MutableStateFlow<String?>(null)

    private val _state = MutableStateFlow<Resource<Unit>>(Resource.Idle())
    val state: StateFlow<Resource<Unit>> = _state

    private val _navigateTo = MutableSharedFlow<String>()
    val navigateTo = _navigateTo.asSharedFlow()
    val validationError = MutableStateFlow<String?>(null)


    fun submitAssessment(patientId: String, vitalId: String) {
        viewModelScope.launch {
            if (!validateInput()) return@launch

            val request = AddVisitRequest(
                patient_id = patientId,
                vital_id = vitalId,
                visit_date = visitDate.value,
                general_health = generalHealth.value,
                on_diet = onDiet.value,
                on_drugs = onDrugs.value,
                comments = comments.value
            )

            _state.value = Resource.Loading()
            when (val result = submitUseCase(request)) {
                is Resource.Success -> {
                    _state.value = Resource.Success(Unit)
                    _navigateTo.emit(Screen.PatientList.route)
                }

                is Resource.Error -> _state.value = Resource.Error(result.message)
                else -> Unit
            }
        }
    }

    private fun validateInput(): Boolean {
        return when {
            visitDate.value.isBlank() -> {
                validationError.value = "Please select a visit date."
                false
            }
            generalHealth.value.isBlank() -> {
                validationError.value = "Please select general health."
                false
            }
            onDiet.value.isBlank() -> {
                validationError.value = "Please indicate if the patient is on a diet."
                false
            }
            onDrugs.value.isBlank() -> {
                validationError.value = "Please indicate if the patient is on drugs."
                false
            }
            else -> {
                validationError.value = null
                true
            }
        }
    }
}
