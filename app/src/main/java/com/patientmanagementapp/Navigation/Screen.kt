package com.patientmanagementapp.Navigation

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Signup : Screen("signup")
    object PatientList : Screen("patient_list")
    object PatientLPatientRegistrationScreen : Screen("patient_registration_screen")
    object VitalsScreen : Screen("vitals_screen/{patientId}") {
        fun createRoute(patientId: String) = "vitals_screen/$patientId"
    }
    object GeneralAssessment : Screen("general_assessment") {

        fun createRoute(patientId: String, vitalId: String, patientName: String): String {
            // Encode patientName to safely handle spaces or special characters
            val encodedName = java.net.URLEncoder.encode(patientName, "UTF-8")
            return "$route?patientId=$patientId&vitalId=$vitalId&patientName=$encodedName"
        }
    }


    object OverweightAssessment : Screen("overweight_assessment") {
        fun createRoute(patientId: String, vitalId: String, patientName: String): String {
            return "$route/$patientId/$vitalId/$patientName"
        }

        const val ARG_PATIENT_ID = "patientId"
        const val ARG_PATIENT_NAME = "patientName"
        const val ARG_VITAL_ID = "vitalId"
    }

}
