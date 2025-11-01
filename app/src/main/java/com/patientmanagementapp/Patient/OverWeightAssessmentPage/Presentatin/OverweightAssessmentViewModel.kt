package com.patientmanagementapp.Patient.OverweightAssessment.Presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.Navigation
import com.patientmanagementapp.Navigation.Screen
import com.patientmanagementapp.Patient.OverWeightAssessmentPage.Dormain.models.OverWeightAssessmentResponse
import com.patientmanagementapp.Patient.OverWeightAssessmentPage.Dormain.models.OverweightAssessmentRequest

import com.patientmanagementapp.Patient.OverWeightAssessmentPage.Dormain.usecase.SubmitOverweightAssessmentUseCase

import com.patientmanagementapp.Utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OverweightAssessmentViewModel @Inject constructor(
    private val submitUseCase: SubmitOverweightAssessmentUseCase
) : ViewModel() {

    // Input fields
    private val _visitDate = MutableStateFlow("")
    val visitDate = _visitDate.asStateFlow()

    private val _generalHealth = MutableStateFlow("")
    val generalHealth = _generalHealth.asStateFlow()

    private val _onDiet = MutableStateFlow("")
    val onDiet = _onDiet.asStateFlow()

    private val _onDrugs = MutableStateFlow("")
    val onDrugs = _onDrugs.asStateFlow()

    private val _comments = MutableStateFlow("")
    val comments = _comments.asStateFlow()

    // API response state
    private val _state = MutableStateFlow<Resource<OverWeightAssessmentResponse>>(Resource.Idle())
    val state = _state.asStateFlow()

    // Navigation
    private val _navigateTo = MutableSharedFlow<String>()
    val navigateTo = _navigateTo.asSharedFlow()

    fun onVisitDateChanged(value: String) { _visitDate.value = value }
    fun onGeneralHealthChanged(value: String) { _generalHealth.value = value }
    fun onOnDietChanged(value: String) { _onDiet.value = value }
    fun onOnDrugsChanged(value: String) { _onDrugs.value = value }
    fun onCommentsChanged(value: String) { _comments.value = value }

    fun submitAssessment(patientId: String, vitalId: String) {
        viewModelScope.launch {
            _state.value = Resource.Loading()
            try {
                val request = OverweightAssessmentRequest(
                    comments = _comments.value,
                    general_health = _generalHealth.value,
                    on_diet = _onDiet.value,
                    on_drugs = _onDrugs.value,
                    patient_id = patientId,
                    vital_id = vitalId,
                    visit_date = _visitDate.value
                )

                val response = submitUseCase(request)
                _state.value = response

                if (response is Resource.Success) {
                    _navigateTo.emit(Screen.PatientList.route) // Replace with actual target
                }

            } catch (e: Exception) {
                _state.value = Resource.Error(e.message ?: "Unknown error")
            }
        }
    }
}
