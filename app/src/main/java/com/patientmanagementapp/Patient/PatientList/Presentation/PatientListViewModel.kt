package com.patientmanagementapp.Patient.PatientList.Presentation


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.patientmanagementapp.Patient.PatientList.Dormain.Model.PatientListResponse
import com.patientmanagementapp.Patient.PatientList.Dormain.UseCases.GetPatientsUseCase

import com.patientmanagementapp.Utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PatientListViewModel @Inject constructor(
    private val getPatientsUseCase: GetPatientsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<Resource<PatientListResponse>>(Resource.Idle())
    val state: StateFlow<Resource<PatientListResponse>> = _state.asStateFlow()

    fun loadPatients() {
        viewModelScope.launch {
            _state.value = Resource.Loading()
            try {
                val response = getPatientsUseCase()
                _state.value = Resource.Success(response)
            } catch (e: Exception) {
                _state.value = Resource.Error(e.message ?: "Unknown error")
            }
        }
    }
}