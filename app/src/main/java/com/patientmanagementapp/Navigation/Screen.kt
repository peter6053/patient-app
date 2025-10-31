package com.patientmanagementapp.Navigation

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Signup : Screen("signup")
   // object PatientList : Screen("patient_list")
    object PatientLPatientRegistrationScreen : Screen("patient_registration_screen")

    object VitalsScreen : Screen("vitals_screen")
}
