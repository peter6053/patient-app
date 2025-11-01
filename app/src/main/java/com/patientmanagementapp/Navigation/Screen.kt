package com.patientmanagementapp.Navigation

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Signup : Screen("signup")
    object PatientList : Screen("patient_list")
    object PatientLPatientRegistrationScreen : Screen("patient_registration_screen")
    object VitalsScreen : Screen("vitals_screen/{patientId}") {
        fun createRoute(patientId: String) = "vitals_screen/$patientId"
    }
    object GeneralAssessment : Screen("general_assessment")
    object OverweightAssessment : Screen("overweight_assessment")
}
