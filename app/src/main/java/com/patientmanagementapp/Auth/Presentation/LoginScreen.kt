package com.example.app.presentation.auth

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.patientmanagementapp.Auth.Presentation.LoginViewModel
import com.patientmanagementapp.Navigation.Screen
import com.patientmanagementapp.Utils.Resource


@Composable
fun LoginScreen(
    onLoginSuccess: (() -> Unit)? = null,
    viewModel: LoginViewModel = hiltViewModel(),
    onNavigateToSignup: () -> Unit,
    navController: NavHostController
) {
    val email by viewModel.email.collectAsState()
    val password by viewModel.password.collectAsState()
    val state by viewModel.state.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Login",
                style = MaterialTheme.typography.headlineMedium
            )

            OutlinedTextField(
                value = email,
                onValueChange = { viewModel.onEmailChanged(it) },
                label = { Text("Email") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = password,
                onValueChange = { viewModel.onPasswordChanged(it) },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = { viewModel.onLoginClick() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Text(text = "Login")
            }

            TextButton(
                onClick = { navController.navigate(Screen.Signup.route) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Don't have an account? Sign Up",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            // Handle login states
            when (val result = state) {
                is Resource.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.padding(top = 16.dp))
                }

                is Resource.Error -> {
                    Text(
                        text = result.message ?: "Unknown error",
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                }

                is Resource.Success -> {
                    // Trigger success navigation only once when data is received
                    LaunchedEffect(result.data) {
                        onLoginSuccess?.invoke()
                    }
                }

                else -> Unit
            }
        }
    }
}

