package com.patientmanagementapp.Navigation

import com.example.app.presentation.auth.LoginScreen
import com.patientmanagementapp.Auth.Presentation.SignupScreen
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.patientmanagementapp.Auth.Presentation.DataStoreViewModel
import com.patientmanagementapp.Patient.PatientList.Presentation.PatientListScreen
import com.patientmanagementapp.Patient.Registration.Presention.PatientRegistrationScreen
import com.patientmanagementapp.Utils.DataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel


@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val dataStoreViewModel: DataStoreViewModel = hiltViewModel()
    val dataStoreManager = dataStoreViewModel.dataStoreManager
    val accessToken by dataStoreManager.accessTokenFlow.collectAsState(initial = null)

    val startDestination = if (!accessToken.isNullOrEmpty()) {
        Screen.PatientList.route
    } else {
        Screen.Login.route
    }

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {

        composable(Screen.PatientList.route) {
            PatientListScreen(navController= navController)
            // PatientListScreen()
        }
        composable(Screen.Login.route) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Screen.PatientList.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                onNavigateToSignup = {
                    navController.navigate(Screen.Signup.route)
                },
                navController = navController
            )
        }

        composable(Screen.Signup.route) {
            SignupScreen(
                viewModel = hiltViewModel(),
                onSignupSuccess = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Signup.route) { inclusive = true }
                    }
                },
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(Screen.PatientLPatientRegistrationScreen.route) {
            PatientRegistrationScreen(navController= navController)
           // PatientListScreen()
        }
    }
}

