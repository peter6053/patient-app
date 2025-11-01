package com.patientmanagementapp.Auth.Presentation


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.patientmanagementapp.Auth.Dormain.Models.LoginResponse
import com.patientmanagementapp.Auth.Dormain.Models.SignupResponse
import com.patientmanagementapp.Auth.Dormain.UseCase.SignupUseCase
import com.patientmanagementapp.Utils.Resource

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val signupUseCase: SignupUseCase
) : ViewModel() {

    private val _signupState = MutableStateFlow<Resource<SignupResponse>?>(null)
    val signupState: StateFlow<Resource<SignupResponse>?> = _signupState

    private val _validationError = MutableStateFlow<String?>(null)
    val validationError: StateFlow<String?> = _validationError

    fun signup(firstName: String, lastName: String, email: String, password: String) {
        viewModelScope.launch {
            // ✅ Validation
            if (firstName.isBlank() || lastName.isBlank() || email.isBlank() || password.isBlank()) {
                _validationError.value = "All fields are required."
                return@launch
            }

            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                _validationError.value = "Please enter a valid email address."
                return@launch
            }

            _validationError.value = null // clear previous error
            _signupState.value = Resource.Loading()

            try {
                val result = signupUseCase(
                    firstname = firstName,
                    lastname = lastName,
                    email = email,
                    password = password
                )
                _signupState.value = result
            } catch (e: Exception) {
                _signupState.value = Resource.Error(e.localizedMessage ?: "Signup failed")
            }
        }
    }

    fun resetState() {
        _signupState.value = null
        _validationError.value = null
    }
}

