package com.patientmanagementapp.Patient.OverWeightAssessmentPage.Presentatin

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
import com.patientmanagementapp.Navigation.Screen
import com.patientmanagementapp.Patient.OverweightAssessment.Presentation.OverweightAssessmentViewModel
import com.patientmanagementapp.Utils.Resource

@Composable
fun OverweightAssessmentScreen(
    navController: NavHostController,
    patientId: String,
    vitalId: String,
    viewModel: OverweightAssessmentViewModel = hiltViewModel()
) {
    val visitDate by viewModel.visitDate.collectAsState()
    val generalHealth by viewModel.generalHealth.collectAsState()
    val onDiet by viewModel.onDiet.collectAsState()
    val onDrugs by viewModel.onDrugs.collectAsState()
    val comments by viewModel.comments.collectAsState()
    val state by viewModel.state.collectAsState()

    val healthOptions = listOf("Good", "Poor")
    val dietOptions = listOf("Yes", "No")
    val drugsOptions = listOf("Yes", "No")

    val navigateTo by viewModel.navigateTo.collectAsState(initial = null)
    LaunchedEffect(navigateTo) {
        navigateTo?.let { navController.navigate(it) }
    }

    LaunchedEffect(state) {
        if (state is Resource.Success) {
            navController.navigate(Screen.PatientList.route) {
                popUpTo(Screen.OverweightAssessment.route) { inclusive = true } // optional: remove this screen from back stack
            }
        }
    }

    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Overweight Assessment", style = MaterialTheme.typography.headlineMedium)

        DatePickerField(label = "Visit Date", value = visitDate,
            onDateSelected = { viewModel.onVisitDateChanged(it) }
        )

        PatientDropdown("General Health", healthOptions, generalHealth) { viewModel.onGeneralHealthChanged(it) }

       // PatientDropdown("Ever been on a diet?", dietOptions, onDiet) { viewModel.onOnDietChanged(it) }

        PatientDropdown("Currently using drugs?", drugsOptions, onDrugs) { viewModel.onOnDrugsChanged(it) }

        PatientTextField(comments, { viewModel.onCommentsChanged(it) }, "Comments")

        PatientButton("Submit Assessment", { viewModel.submitAssessment(patientId, vitalId) }, isLoading = state is Resource.Loading)

        if (state is Resource.Error) {
            Text((state as Resource.Error).message, color = MaterialTheme.colorScheme.error)
        }
        if (state is Resource.Success) {
            navController.navigate(Screen.PatientList.route) {
                popUpTo(Screen.OverweightAssessment.route) { inclusive = true } // optional: remove this screen from back stack
            }
        }

    }
}
