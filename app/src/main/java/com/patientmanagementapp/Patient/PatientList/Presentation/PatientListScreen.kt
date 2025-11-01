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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.ExitToApp
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
import com.patientmanagementapp.Patient.Registration.Data.Local.Dao.PatientDao
import com.patientmanagementapp.Patient.Registration.Data.Local.Entitity.RegisterPatientEntity
import com.patientmanagementapp.Patient.Vitals.Data.Local.Dao.VitalsDao
import com.patientmanagementapp.Patient.Vitals.Data.Local.Entity.VitalsEntity
import com.patientmanagementapp.Utils.ReportHeader
import com.patientmanagementapp.Utils.Resource
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PatientListScreen(
    navController: NavHostController,
    viewModel: PatientListViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()
    var localPatients by remember { mutableStateOf(listOf<RegisterPatientEntity>()) }
    var localVitals by remember { mutableStateOf(listOf<VitalsEntity>()) }
    var selectedDate by remember { mutableStateOf<LocalDate?>(null) }

    LaunchedEffect(Unit) {
        localPatients = viewModel.getLocalPatients()
        localVitals = viewModel.getLocalVitals()
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
                modifier = Modifier.shadow(4.dp),
                actions = {
                    IconButton(onClick = {
                        navController.navigate(Screen.Login.route) {
                            popUpTo(0)
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Default.ExitToApp,
                            contentDescription = "Logout",
                            tint = Color.Black
                        )
                    }
                }
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
                .background(Color(0xFFFFF8E1))
        ) {
            when (state) {
                is Resource.Loading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                is Resource.Error -> Text(
                    text = (state as Resource.Error).message,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.Center)
                )
                is Resource.Success -> {
                    LaunchedEffect(state) {
                        localPatients = viewModel.getLocalPatients()
                        localVitals = viewModel.getLocalVitals()
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Patient Listing",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            ),
                            modifier = Modifier.padding(bottom = 16.dp)
                        )

                        ReportHeader(
                            selectedDate = selectedDate,
                            onDateSelected = { date ->
                                selectedDate = date
                                viewModel.filterPatientsByDate(date)
                            }
                        )

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
                                .weight(1f)
                                .border(0.5.dp, Color(0xFF9CCC65))
                        ) {
                            itemsIndexed(localPatients) { index, patient ->
                                val bgColor = if (index % 2 == 0) Color.White else Color(0xFFE8F5E9)
                                val vitals = localVitals.find { it.patient_id == patient.unique }
                                val bmiStatus = vitals?.bmi?.toFloatOrNull()?.let { bmi ->
                                    when {
                                        bmi < 18.5f -> "Underweight"
                                        bmi in 18.5f..24.9f -> "Normal"
                                        else -> "Overweight"
                                    }
                                } ?: "Underweight"

                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(bgColor)
                                        .padding(vertical = 8.dp)
                                ) {
                                    TableCell("${patient.firstname} ${patient.lastname}", Modifier.weight(2f))
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




