package com.patientmanagementapp.Patient.Vitals.Presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
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

    val scrollState = rememberScrollState()

    LaunchedEffect(Unit) {
        viewModel.navigateTo.collect { route ->
            navController.navigate(route) {
                popUpTo("vitals_screen") { inclusive = true }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF7F5FF))
            .padding(horizontal = 24.dp, vertical = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Patient Vitals",
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                ),
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Visit Date
            DatePickerField(
                label = "Visit Date",
                value = visitDate,
                onDateSelected = { viewModel.onVisitDateChanged(it) },
                modifier = Modifier.fillMaxWidth()
            )

            PatientTextField(
                value = height,
                onValueChange = { viewModel.onHeightChanged(it) },
                label = "Height (CM)",
                modifier = Modifier.fillMaxWidth()
            )

            PatientTextField(
                value = weight,
                onValueChange = { viewModel.onWeightChanged(it) },
                label = "Weight (KG)",
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = bmi,
                onValueChange = {},
                label = { Text("BMI", color = Color.Black) },
                modifier = Modifier.fillMaxWidth(),
                readOnly = true,
                enabled = false,
                colors = OutlinedTextFieldDefaults.colors(
                    disabledTextColor = Color.Black,
                    disabledBorderColor = Color.Black.copy(alpha = 0.3f),
                    disabledLabelColor = Color.Black.copy(alpha = 0.7f)
                ),
                textStyle = LocalTextStyle.current.copy(color = Color.Black)
            )

            PatientButton(
                text = "Submit Vitals",
                onClick = { viewModel.submitVitals(patientId) },
                isLoading = state is Resource.Loading
            )

            if (state is Resource.Error) {
                Text(
                    text = (state as Resource.Error).message,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

