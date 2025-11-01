package com.patientmanagementapp.Patient.PatientList.Presentation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.patientmanagementapp.Navigation.Screen
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
                title = { Text("Intelli App") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFFFF8E1),
                    titleContentColor = Color.Black
                ),
                modifier = Modifier.shadow(4.dp)
            )

        },

                floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(Screen.PatientLPatientRegistrationScreen.route) }
            ) {
                Text("+")
            }
        },

        containerColor = Color(0xFFFFF8E1)
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color(0xFFFFF8E1)) // light yellow background
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

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Title
                        Text(
                            text = "Patient Listing",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            ),
                            modifier = Modifier.padding(bottom = 16.dp)
                        )

                        // Date field
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .border(1.dp, Color.Black, RoundedCornerShape(8.dp))
                                .padding(8.dp)
                        ) {
                            Text(
                                text = "Date",
                                modifier = Modifier.weight(1f),
                                fontWeight = FontWeight.Medium
                            )
                            Text(
                                text = "..... / ..... / ......",
                                modifier = Modifier.weight(2f)
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color(0xFF9CCC65))
                                .padding(vertical = 8.dp)
                        ) {
                            TableHeaderCell("Patient Name", Modifier.weight(2f))
                            TableHeaderCell("Date of Birth", Modifier.weight(1f))
                            TableHeaderCell("BMI Status", Modifier.weight(1f))
                        }

                        LazyColumn(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f) // take remaining height
                                .border(0.5.dp, Color(0xFF9CCC65))
                        ) {
                            itemsIndexed(patients) { index, patient ->
                                val bgColor = if (index % 2 == 0) Color.White else Color(0xFFE8F5E9)
                                val bmiStatus = when (patient.firstname.lowercase()) {
                                    "john" -> "Normal"
                                    "jane" -> "Overweight"
                                    "james" -> "Underweight"
                                    "julia" -> "Normal"
                                    else -> "Normal"
                                }

                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(bgColor)
                                        .padding(vertical = 8.dp)
                                        .clickable { /* navigate to details if needed */ }
                                ) {
                                    TableCell(
                                        "${patient.firstname} ${patient.lastname}",
                                        Modifier.weight(2f)
                                    )
                                    TableCell(patient.dob ?: "N/A", Modifier.weight(1f))
                                    TableCell(bmiStatus, Modifier.weight(1f))
                                }
                            }
                        }

                    }
                }

                else -> Unit
            }
        }
    }
}

@Composable
fun TableHeaderCell(text: String, modifier: Modifier) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyLarge.copy(
            fontWeight = FontWeight.Bold,
            color = Color.White
        ),
        modifier = modifier.padding(horizontal = 8.dp)
    )
}

@Composable
fun TableCell(text: String, modifier: Modifier) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyMedium.copy(color = Color.Black),
        modifier = modifier.padding(horizontal = 8.dp)
    )
}



