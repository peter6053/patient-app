package com.patientmanagementapp.PatientRegistration.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.patientmanagementapp.Patient.Registration.Dormain.Models.RegisterPatientRequest
import com.patientmanagementapp.Patient.Registration.Dormain.Models.RegisterPatientResponse
import com.patientmanagementapp.Patient.Registration.Dormain.UseCase.RegisterPatientUseCase
import com.patientmanagementapp.Utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class PatientRegistrationViewModel @Inject constructor(
    private val registerPatientUseCase: RegisterPatientUseCase
) : ViewModel() {

    private val _firstName = MutableStateFlow("")
    val firstName: StateFlow<String> = _firstName.asStateFlow()

    private val _lastName = MutableStateFlow("")
    val lastName: StateFlow<String> = _lastName.asStateFlow()

    private val _dob = MutableStateFlow("")
    val dob: StateFlow<String> = _dob.asStateFlow()

    private val _gender = MutableStateFlow("")
    val gender: StateFlow<String> = _gender.asStateFlow()

    private val _regDate = MutableStateFlow("")
    val regDate: StateFlow<String> = _regDate.asStateFlow()

    // Auto-generate patient ID using UUID
    private val _patientId = MutableStateFlow(generatePatientId())
    val patientId: StateFlow<String> = _patientId.asStateFlow()

    private val _state = MutableStateFlow<Resource<RegisterPatientResponse>>(Resource.Idle())
    val state: StateFlow<Resource<RegisterPatientResponse>> = _state.asStateFlow()

    init {
        _patientId.value = generatePatientId()
    }

    private fun generatePatientId(): String {
        return UUID.randomUUID().toString().replace("-", "")
    }

    fun onFirstNameChanged(value: String) { _firstName.value = value }
    fun onLastNameChanged(value: String) { _lastName.value = value }
    fun onDobChanged(value: String) { _dob.value = value }
    fun onGenderChanged(value: String) { _gender.value = value }
    fun onRegDateChanged(value: String) { _regDate.value = value }

    fun registerPatient() {
        viewModelScope.launch {
            _state.value = Resource.Loading()
            val request = RegisterPatientRequest(
                dob = _dob.value,
                firstname = _firstName.value,
                gender = _gender.value,
                lastname = _lastName.value,
                reg_date = _regDate.value,
                unique = _patientId.value
            )
            _state.value = registerPatientUseCase(request)
        }
    }
}
