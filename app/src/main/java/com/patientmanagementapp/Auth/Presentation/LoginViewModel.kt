package com.patientmanagementapp.Auth.Presentation


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.patientmanagementapp.Auth.Dormain.Models.LoginResponse
import com.patientmanagementapp.Auth.Dormain.UseCase.LoginUseCase
import com.patientmanagementapp.Utils.DataStoreManager
import com.patientmanagementapp.Utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()

    private val _state = MutableStateFlow<Resource<LoginResponse>>(Resource.Idle())
    val state: StateFlow<Resource<LoginResponse>> = _state.asStateFlow()

    // New: validation error state
    private val _validationError = MutableStateFlow<String?>(null)
    val validationError: StateFlow<String?> = _validationError.asStateFlow()

    fun onEmailChanged(value: String) {
        _email.value = value
    }

    fun onPasswordChanged(value: String) {
        _password.value = value
    }

    fun validateAndLogin() {
        viewModelScope.launch {
            if (_email.value.isBlank() || _password.value.isBlank()) {
                _validationError.value = "Please fill in both email and password"
                return@launch
            }

            _validationError.value = null
            onLoginClick()
        }
    }

    fun onLoginClick() {
        viewModelScope.launch {
            _state.value = Resource.Loading()
            try {
                val result: Resource<LoginResponse> = loginUseCase(_email.value, _password.value)
                if (result is Resource.Success && result.data != null) {
                    // Save token to DataStore
                    dataStoreManager.saveAccessToken(result.data.data.access_token)
                }
                _state.value = result
            } catch (e: Exception) {
                _state.value = Resource.Error(e.message ?: "Unknown error occurred")
            }
        }
    }
}


