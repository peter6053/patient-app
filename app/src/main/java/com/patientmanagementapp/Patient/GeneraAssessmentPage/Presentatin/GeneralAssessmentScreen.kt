package com.patientmanagementapp.Patient.GeneraAssessmentPage.Presentatin

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.patientmanagementapp.Components.DatePickerField
import com.patientmanagementapp.Components.PatientButton
import com.patientmanagementapp.Components.PatientDropdown
import com.patientmanagementapp.Components.PatientTextField
import com.patientmanagementapp.Utils.Resource

@Composable
fun GeneralAssessmentScreen(
    patientId: String,
    vitalId: String,
    viewModel: GeneralAssessmentViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val visitDate by viewModel.visitDate.collectAsState()
    val generalHealth by viewModel.generalHealth.collectAsState()
    val onDiet by viewModel.onDiet.collectAsState()
    val onDrugs by viewModel.onDrugs.collectAsState()
    val comments by viewModel.comments.collectAsState()
    val state by viewModel.state.collectAsState()

    val healthOptions = listOf("Good", "Poor")
    val dietOptions = listOf("Yes", "No")
    val drugOptions = listOf("Yes", "No")

    val navigateTo by viewModel.navigateTo.collectAsState(initial = null)
    LaunchedEffect(navigateTo) {
        navigateTo?.let { navController.navigate(it) }
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("General Assessment", style = MaterialTheme.typography.headlineMedium)

        DatePickerField(
            label = "Visit Date",
            value = visitDate,
            onDateSelected = { viewModel.visitDate.value = it }
        )

        PatientDropdown(
            label = "General Health",
            options = healthOptions,
            selectedOption = generalHealth,
            onOptionSelected = { viewModel.generalHealth.value = it }
        )

        PatientDropdown(
            label = "Ever been on a diet?",
            options = dietOptions,
            selectedOption = onDiet,
            onOptionSelected = { viewModel.onDiet.value = it }
        )

        PatientDropdown(
            label = "On drugs?",
            options = drugOptions,
            selectedOption = onDrugs,
            onOptionSelected = { viewModel.onDrugs.value = it }
        )

        PatientTextField(
            value = comments,
            onValueChange = { viewModel.comments.value = it },
            label = "Comments"
        )

        PatientButton(
            text = "Submit",
            onClick = { viewModel.submitAssessment(patientId, vitalId) },
            isLoading = state is Resource.Loading
        )
    }
}
