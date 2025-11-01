package com.patientmanagementapp.Patient.GeneraAssessmentPage.Presentatin

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.patientmanagementapp.Components.DatePickerField
import com.patientmanagementapp.Components.PatientButton
import com.patientmanagementapp.Components.PatientDropdown
import com.patientmanagementapp.Components.PatientTextField
import com.patientmanagementapp.Navigation.Screen
import com.patientmanagementapp.Utils.Resource

@Composable
fun GeneralAssessmentScreen(
    patientId: String,
    vitalId: String,
    patientName: String,
    viewModel: GeneralAssessmentViewModel = hiltViewModel(),
    navController: NavHostController,
) {
    val visitDate by viewModel.visitDate.collectAsState()
    val generalHealth by viewModel.generalHealth.collectAsState()
    val onDiet by viewModel.onDiet.collectAsState()
    val onDrugs by viewModel.onDrugs.collectAsState()
    val comments by viewModel.comments.collectAsState()
    val state by viewModel.state.collectAsState()
    val validationError by viewModel.validationError.collectAsState()



    val healthOptions = listOf("Good", "Poor")
    val dietOptions = listOf("Yes", "No")
    val drugOptions = listOf("Yes", "No")

    val navigateTo by viewModel.navigateTo.collectAsState(initial = null)
    LaunchedEffect(navigateTo) {
        navigateTo?.let { navController.navigate(it) }
    }

    LaunchedEffect(state) {
        if (state is Resource.Success) {
            navController.navigate(Screen.PatientList.route) {
                popUpTo(Screen.GeneralAssessment.route) { inclusive = true }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF7F5FF)),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .wrapContentHeight()
                .padding(start = 16.dp)
                .verticalScroll(rememberScrollState())
                .shadow(12.dp, RoundedCornerShape(24.dp)),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(24.dp)
        ) {
            // Title
            Text(
                text = "Patient Visit Form B - General Assessment",
                style = MaterialTheme.typography.titleMedium.copy(color = Color.Black),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                textAlign = TextAlign.Center
            )

            DatePickerField(
                label = "Visit Date",
                value = visitDate,
                onDateSelected = { viewModel.visitDate.value = it }
            )

            Text(
                text = "General Health?",
                style = MaterialTheme.typography.titleMedium.copy(color = Color.Black)
            )
            healthOptions.forEach { option ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { viewModel.generalHealth.value = option }
                        .padding(vertical = 2.dp)
                ) {
                    RadioButton(
                        selected = generalHealth == option,
                        onClick = { viewModel.generalHealth.value = option }
                    )
                    Text(
                        text = option,
                        style = MaterialTheme.typography.bodyLarge.copy(color = Color.Black)
                    )
                }
            }

            // Ever been on a diet
            Text(
                text = "Ever been on a diet to loos weight?",
                style = MaterialTheme.typography.titleMedium.copy(color = Color.Black)
            )
            dietOptions.forEach { option ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { viewModel.onDiet.value = option }
                        .padding(vertical = 2.dp)
                ) {
                    RadioButton(
                        selected = onDiet == option,
                        onClick = { viewModel.onDiet.value = option }
                    )
                    Text(
                        text = option,
                        style = MaterialTheme.typography.bodyLarge.copy(color = Color.Black)
                    )
                }
            }

            // On Drugs
            Text(
                text = "On drugs?",
                style = MaterialTheme.typography.titleMedium.copy(color = Color.Black)
            )
            drugOptions.forEach { option ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { viewModel.onDrugs.value = option }
                        .padding(vertical = 2.dp)
                ) {
                    RadioButton(
                        selected = onDrugs == option,
                        onClick = { viewModel.onDrugs.value = option }
                    )
                    Text(
                        text = option,
                        style = MaterialTheme.typography.bodyLarge.copy(color = Color.Black)
                    )
                }
            }

            // Comments field
            PatientTextField(
                value = comments,
                onValueChange = { viewModel.comments.value = it },
                label = "Comments"
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = { navController.popBackStack() },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD6EAD6))
                ) {
                    Text("Close")
                }

                Button(
                    onClick = { viewModel.submitAssessment(patientId, vitalId,patientName) },
                    enabled = state !is Resource.Loading,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD6EAD6))
                ) {
                    if (state is Resource.Loading) {
                        CircularProgressIndicator(
                            color = Color.Gray,
                            strokeWidth = 2.dp,
                            modifier = Modifier.size(20.dp)
                        )
                    } else {
                        Text("Save")
                    }
                }
            }

            // Validation or error messages
            validationError?.let {
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            if (state is Resource.Error) {
                Text(
                    text = (state as Resource.Error).message,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}

