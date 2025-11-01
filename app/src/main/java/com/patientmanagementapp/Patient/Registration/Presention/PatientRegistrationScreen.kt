package com.patientmanagementapp.Patient.Registration.Presention


import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.patientmanagementapp.Utils.Resource
import androidx.compose.material3.*
import com.patientmanagementapp.Components.DatePickerField
import com.patientmanagementapp.Components.PatientButton
import com.patientmanagementapp.Components.PatientDropdown
import com.patientmanagementapp.Components.PatientTextField
import com.patientmanagementapp.Navigation.Screen
import com.patientmanagementapp.PatientRegistration.presentation.PatientRegistrationViewModel

@Composable
fun PatientRegistrationScreen(
    viewModel: PatientRegistrationViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val firstName by viewModel.firstName.collectAsState()
    val lastName by viewModel.lastName.collectAsState()
    val dob by viewModel.dob.collectAsState()
    val gender by viewModel.gender.collectAsState()
    val regDate by viewModel.regDate.collectAsState()
    val patientId by viewModel.patientId.collectAsState()
    val state by viewModel.state.collectAsState()

    val genderOptions = listOf("Male", "Female", "Other")

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Patient Registration", style = MaterialTheme.typography.headlineMedium)

            PatientTextField(
                value = firstName,
                onValueChange = { viewModel.onFirstNameChanged(it) },
                label = "First Name"
            )

            PatientTextField(
                value = lastName,
                onValueChange = { viewModel.onLastNameChanged(it) },
                label = "Last Name"
            )

            // Use DatePickerField for DOB
            DatePickerField(
                label = "Date of Birth",
                value = dob,
                onDateSelected = { viewModel.onDobChanged(it) }
            )

            PatientDropdown(
                label = "Gender",
                options = genderOptions,
                selectedOption = gender,
                onOptionSelected = { viewModel.onGenderChanged(it) }
            )

            // Use DatePickerField for Registration Date
            DatePickerField(
                label = "Registration Date",
                value = regDate,
                onDateSelected = { viewModel.onRegDateChanged(it) }
            )

            PatientButton(
                text = "Register",
                onClick = { viewModel.registerPatient() },
                isLoading = state is Resource.Loading
            )

            when (state) {
                is Resource.Error -> Text(
                    text = (state as Resource.Error).message,
                    color = MaterialTheme.colorScheme.error
                )

                is Resource.Success -> {
                    val response = (state as Resource.Success).data
                   // val patientId = response?.data?.proceed
                    if (patientId.isNotEmpty()) {
                        LaunchedEffect(patientId) {
                            navController.navigate(Screen.VitalsScreen.createRoute(patientId)) {
                                popUpTo(Screen.VitalsScreen.route) { inclusive = true }
                            }
                        }
                    }
                }



                else -> Unit
            }
        }
    }
}

