package com.patientmanagementapp.Auth.Presentation

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.patientmanagementapp.Auth.Dormain.Models.LoginResponse
import com.patientmanagementapp.Auth.Dormain.UseCase.LoginUseCase
import com.patientmanagementapp.Utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _email = MutableStateFlow("")
    var email: StateFlow<String> = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()

    private val _state = MutableStateFlow<Resource<LoginResponse>>(Resource.Loading())
    val state: StateFlow<Resource<LoginResponse>> = _state.asStateFlow()

    fun onEmailChanged(value: String) {
        _email.value = value
    }

    fun onPasswordChanged(value: String) {
        _password.value = value
    }

    fun onLoginClick() {
        viewModelScope.launch {
            _state.value = Resource.Loading()
            try {
                val result: Resource<LoginResponse> = loginUseCase(_email.value, _password.value)

                _state.value = result
            } catch (e: Exception) {
                _state.value = Resource.Error(e.message ?: "Unknown error occurred")
            }
        }
    }
}

