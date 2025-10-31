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


    fun signup(firstName: String,lastName: String, email: String, password: String) {
        viewModelScope.launch {
            _signupState.value = Resource.Loading()
            try {
                val result = signupUseCase(firstname = firstName, email = email, password =  password, lastname = lastName)
                _signupState.value = result
            } catch (e: Exception) {
                _signupState.value = Resource.Error(e.localizedMessage ?: "Signup failed")
            }
        }
    }

    fun resetState() {
        _signupState.value = null
    }
}
