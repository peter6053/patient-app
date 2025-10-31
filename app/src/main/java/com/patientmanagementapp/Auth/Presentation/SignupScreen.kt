package com.patientmanagementapp.Auth.Presentation

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.patientmanagementapp.Components.PatientButton
import com.patientmanagementapp.Components.PatientPasswordField
import com.patientmanagementapp.Components.PatientTextField
import com.patientmanagementapp.Utils.Resource



@Composable
fun SignupScreen(
    viewModel: SignupViewModel = hiltViewModel(),
    onSignupSuccess: (() -> Unit)? = null,
    onNavigateBack: () -> Unit,

) {
    val signupState by viewModel.signupState.collectAsState()

    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Sign Up",
                style = MaterialTheme.typography.headlineMedium
            )

            PatientTextField(
                value = firstName,
                onValueChange = { firstName = it },
                label = "First Name"
            )

            PatientTextField(
                value = lastName,
                onValueChange = { lastName = it },
                label = "Last Name"
            )

            PatientTextField(
                value = email,
                onValueChange = { email = it },
                label = "Email"
            )

            PatientPasswordField(
                value = password,
                onValueChange = { password = it },
                label = "Password"
            )

            when (signupState) {
                is Resource.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.padding(top = 16.dp))
                }
                is Resource.Error -> {
                    Text(
                        text = (signupState as Resource.Error).message,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                }
                is Resource.Success -> {
                    val data = (signupState as Resource.Success).data
                    LaunchedEffect(data) {
                        data?.let {
                            onSignupSuccess?.invoke()
                            viewModel.resetState()
                        }
                    }
                }
                null -> {}
                is Resource.Idle<*> -> TODO()
            }

            PatientButton(
                text = "Sign Up",
                onClick = {
                    viewModel.signup(
                        firstName = firstName,
                        lastName = lastName,
                        email = email,
                        password = password
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            )
        }
    }
}


