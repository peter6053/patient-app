package com.patientmanagementapp.Auth.Presentation

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.patientmanagementapp.Components.PatientButton
import com.patientmanagementapp.Components.PatientPasswordField
import com.patientmanagementapp.Components.PatientTextField
import com.patientmanagementapp.Utils.Resource
@Composable
fun SignupScreen(
    viewModel: SignupViewModel = hiltViewModel(),
    onSignupSuccess: (() -> Unit)? = null,
    onNavigateBack: () -> Unit,
) {
    val signupState by viewModel.signupState.collectAsState()

    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    listOf(Color(0xFFF8F8F8), Color(0xFFDCDCDC))
                )
            )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 80.dp)
        ) {
            // App Logo / Title
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color.White)
                    .padding(horizontal = 24.dp, vertical = 8.dp)
                    .shadow(2.dp, RoundedCornerShape(10.dp))
            ) {
                Text(
                    text = "IntelliSOFT",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF5A4FCF)
                    )
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Manage Your Patients, With IntelliSOFT",
                color = Color.Gray,
                fontSize = 14.sp,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(40.dp))

            // Purple curved background
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(topStart = 120.dp))
                    .background(Color(0xFF8A56F0))
                    .padding(24.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // 🟣 TabRow (Login / Sign-up)
                    var selectedTab by remember { mutableStateOf(1) } // 1 = Sign-up

                    TabRow(
                        selectedTabIndex = selectedTab,
                        containerColor = Color.Transparent,
                        contentColor = Color.White,
                        indicator = { tabPositions ->
                            TabRowDefaults.Indicator(
                                Modifier
                                    .tabIndicatorOffset(tabPositions[selectedTab])
                                    .height(2.dp),
                                color = Color.White
                            )
                        },
                        divider = {}
                    ) {
                        Tab(
                            selected = selectedTab == 0,
                            onClick = {
                                selectedTab = 0
                                onNavigateBack() // Go to login
                            },
                            text = {
                                Text(
                                    "Login",
                                    color = Color.White.copy(alpha = if (selectedTab == 0) 1f else 0.5f)
                                )
                            }
                        )
                        Tab(
                            selected = selectedTab == 1,
                            onClick = { selectedTab = 1 },
                            text = { Text("Sign-up", color = Color.White) }
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Hi Doctor..",
                        color = Color.White,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(Alignment.Start)
                    )

                    // Input Fields
                    PatientTextField(
                        value = firstName,
                        onValueChange = { firstName = it },
                        label = "First Name",
                        modifier = Modifier.fillMaxWidth()
                    )

                    PatientTextField(
                        value = lastName,
                        onValueChange = { lastName = it },
                        label = "Last Name",
                        modifier = Modifier.fillMaxWidth()
                    )

                    PatientTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = "Email",
                        modifier = Modifier.fillMaxWidth()
                    )

                    PatientPasswordField(
                        value = password,
                        onValueChange = { password = it },
                        label = "Password",
                        modifier = Modifier.fillMaxWidth()
                    )

                    // State Handling
                    when (signupState) {
                        is Resource.Loading -> {
                            CircularProgressIndicator(color = Color.White)
                        }

                        is Resource.Error -> {
                            Text(
                                text = (signupState as Resource.Error).message,
                                color = Color.Red,
                                fontSize = 14.sp
                            )
                        }

                        is Resource.Success -> {
                            val data = (signupState as Resource.Success).data
                            LaunchedEffect(data) {
                                data?.let {
                                    onSignupSuccess?.invoke()
                                    viewModel.resetState()
                                }
                            }
                        }

                        else -> {}
                    }

                    // Sign Up Button
                    Button(
                        onClick = {
                            viewModel.signup(
                                firstName = firstName,
                                lastName = lastName,
                                email = email,
                                password = password
                            )
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                    ) {
                        Text(
                            text = "Sign Up",
                            color = Color(0xFF8A56F0),
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    TextButton(onClick = onNavigateBack) {
                        Text(
                            text = "Already have an account? Login",
                            color = Color.White,
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }
    }
}




//@Composable
//fun SignupScreen(
//    viewModel: SignupViewModel = hiltViewModel(),
//    onSignupSuccess: (() -> Unit)? = null,
//    onNavigateBack: () -> Unit,
//
//) {
//    val signupState by viewModel.signupState.collectAsState()
//
//    var firstName by remember { mutableStateOf("") }
//    var lastName by remember { mutableStateOf("") }
//    var email by remember { mutableStateOf("") }
//    var password by remember { mutableStateOf("") }
//
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(24.dp),
//        contentAlignment = Alignment.Center
//    ) {
//        Column(
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.spacedBy(16.dp),
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            Text(
//                text = "Sign Up",
//                style = MaterialTheme.typography.headlineMedium
//            )
//
//            PatientTextField(
//                value = firstName,
//                onValueChange = { firstName = it },
//                label = "First Name"
//            )
//
//            PatientTextField(
//                value = lastName,
//                onValueChange = { lastName = it },
//                label = "Last Name"
//            )
//
//            PatientTextField(
//                value = email,
//                onValueChange = { email = it },
//                label = "Email"
//            )
//
//            PatientPasswordField(
//                value = password,
//                onValueChange = { password = it },
//                label = "Password"
//            )
//
//            when (signupState) {
//                is Resource.Loading -> {
//                    CircularProgressIndicator(modifier = Modifier.padding(top = 16.dp))
//                }
//                is Resource.Error -> {
//                    Text(
//                        text = (signupState as Resource.Error).message,
//                        color = MaterialTheme.colorScheme.error,
//                        modifier = Modifier.padding(top = 16.dp)
//                    )
//                }
//                is Resource.Success -> {
//                    val data = (signupState as Resource.Success).data
//                    LaunchedEffect(data) {
//                        data?.let {
//                            onSignupSuccess?.invoke()
//                            viewModel.resetState()
//                        }
//                    }
//                }
//                null -> {}
//                is Resource.Idle<*> -> TODO()
//            }
//
//            PatientButton(
//                text = "Sign Up",
//                onClick = {
//                    viewModel.signup(
//                        firstName = firstName,
//                        lastName = lastName,
//                        email = email,
//                        password = password
//                    )
//                },
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(48.dp)
//            )
//        }
//    }
//}


