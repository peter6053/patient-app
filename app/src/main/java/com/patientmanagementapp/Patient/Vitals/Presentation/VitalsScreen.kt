package com.patientmanagementapp.Patient.Vitals.Presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.patientmanagementapp.Components.DatePickerField
import com.patientmanagementapp.Components.PatientButton
import com.patientmanagementapp.Components.PatientTextField
import com.patientmanagementapp.Utils.Resource
import com.patientmanagementapp.Vitals.presentation.VitalsViewModel

@Composable
fun VitalsScreen(
    navController: NavHostController,
    viewModel: VitalsViewModel = hiltViewModel(),
    patientId: String
) {
    val visitDate by viewModel.visitDate.collectAsState()
    val height by viewModel.height.collectAsState()
    val weight by viewModel.weight.collectAsState()
    val bmi by viewModel.bmi.collectAsState()
    val state by viewModel.state.collectAsState()

    val scope = rememberCoroutineScope()

    // Observe navigation events
    LaunchedEffect(Unit) {
        viewModel.navigateTo.collect { route ->
            navController.navigate(route) {
                popUpTo("vitals_screen") { inclusive = true }
            }
        }
    }

    Column(modifier = Modifier.padding(24.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text("Patient Vitals", style = MaterialTheme.typography.headlineMedium)

        DatePickerField(
            label = "Visit Date",
            value = visitDate,
            onDateSelected =  { viewModel.onVisitDateChanged(it) },
        )

        PatientTextField(
            value = height,
            onValueChange = { viewModel.onHeightChanged(it) },
            label = "Height (CM)"
        )

        PatientTextField(
            value = weight,
            onValueChange = { viewModel.onWeightChanged(it) },
            label = "Weight (KG)"
        )

        PatientTextField(
            value = bmi,
            onValueChange = {},
            label = "BMI",
            modifier = Modifier.fillMaxWidth(),
            isPassword = false // read-only
        )

        PatientButton(
            text = "Submit Vitals",
            onClick = { viewModel.submitVitals(patientId) },
            isLoading = state is Resource.Loading
        )

        if (state is Resource.Error) {
            Text(
                text = (state as Resource.Error).message,
                color = MaterialTheme.colorScheme.error
            )
        }
    }
}
