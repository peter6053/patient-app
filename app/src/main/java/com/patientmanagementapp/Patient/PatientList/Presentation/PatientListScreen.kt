package com.patientmanagementapp.Patient.PatientList.Presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.patientmanagementapp.Navigation.Screen
import com.patientmanagementapp.Patient.PatientList.Dormain.Model.PatientListResponse
import com.patientmanagementapp.Utils.Resource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PatientListScreen(
    navController: NavHostController,
    viewModel: PatientListViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadPatients()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Intelli App") }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(Screen.PatientLPatientRegistrationScreen.route) }
            ) {
                Text("+")
            }
        },
        content = { paddingValues ->
            Box(modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
            ) {
                when (state) {
                    is Resource.Loading -> {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }
                    is Resource.Error -> {
                        Text(
                            text = (state as Resource.Error).message,
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                    is Resource.Success -> {
                        val patients = (state as Resource.Success).data?.data ?: emptyList()
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.spacedBy(12.dp),
                            contentPadding = PaddingValues(16.dp)
                        ) {
                            items(patients) { patient ->
                                PatientItem(patient = patient) {
                                    // Optional: onClick to go to patient details or vitals
                                }
                            }
                        }
                    }
                    else -> Unit
                }
            }
        }
    )
}

@Composable
fun PatientItem(patient: PatientListResponse.Data, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "${patient.firstname} ${patient.lastname}",
                style = MaterialTheme.typography.titleMedium
            )
            Text(text = "DOB: ${patient.dob}")
            Text(text = "Gender: ${patient.gender}")
            Text(text = "Registered: ${patient.reg_date}")
        }
    }
}

