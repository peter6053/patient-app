package com.patientmanagementapp.Navigation

import com.example.app.presentation.auth.LoginScreen
import com.patientmanagementapp.Auth.Presentation.SignupScreen
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable




@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Login.route,
        modifier = modifier
    ) {
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

//        composable(Screen.PatientList.route) {
//            PatientListScreen()
//        }
    }
}
