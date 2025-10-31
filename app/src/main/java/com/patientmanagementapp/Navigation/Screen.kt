package com.patientmanagementapp.Navigation

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Signup : Screen("signup")
    object PatientList : Screen("patient_list")
    // Add more screens later: VitalsForm, AssessmentForm, etc.
}
