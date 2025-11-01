package com.patientmanagementapp.Patient.Registration.Presention


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.patientmanagementapp.Utils.Resource
import androidx.compose.material3.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
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

    val genderOptions = listOf("Male", "Female")

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
                .shadow(12.dp, RoundedCornerShape(24.dp)),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(24.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color(0xFF6A4EF6))
                        .padding(vertical = 12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "➕ Add New Patient",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                var selectedGender by remember { mutableStateOf(gender.ifEmpty { "Male" }) }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    genderOptions.forEach { option ->
                        val isSelected = selectedGender == option
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(10.dp))
                                .background(
                                    if (isSelected) Color(0xFF6A4EF6)
                                    else Color(0xFFF1F0F7)
                                )
                                .clickable {
                                    selectedGender = option
                                    viewModel.onGenderChanged(option)
                                }
                                .padding(vertical = 10.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = option,
                                color = if (isSelected) Color.White else Color.Gray,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }

                PatientTextField(
                    value = firstName,
                    onValueChange = { viewModel.onFirstNameChanged(it) },
                    label = "First Name",
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color(0xFFF8F8F8))
                )

                PatientTextField(
                    value = lastName,
                    onValueChange = { viewModel.onLastNameChanged(it) },
                    label = "Last Name",
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color(0xFFF8F8F8))
                )

                DatePickerField(
                    label = "Date of Birth",
                    value = dob,
                    onDateSelected = { viewModel.onDobChanged(it) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color(0xFFF8F8F8))
                )

                PatientDropdown(
                    label = "Gender",
                    options = listOf("Male", "Female", "Other"),
                    selectedOption = gender,
                    onOptionSelected = { viewModel.onGenderChanged(it) },

                )

                DatePickerField(
                    label = "Registration Date",
                    value = regDate,
                    onDateSelected = { viewModel.onRegDateChanged(it) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color(0xFFF8F8F8))
                )

                Button(
                    onClick = { viewModel.validateAndRegister() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .clip(RoundedCornerShape(16.dp)),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6A4EF6))
                ) {
                    if (state is Resource.Loading) {
                        CircularProgressIndicator(color = Color.White, strokeWidth = 2.dp)
                    } else {
                        Text(
                            text = "ADD",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    }
                }

                when (state) {
                    is Resource.Error -> Text(
                        text = (state as Resource.Error).message,
                        color = Color.Red,
                        fontSize = 14.sp
                    )

                    is Resource.Success -> {
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
}



