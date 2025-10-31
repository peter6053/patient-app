package com.patientmanagementapp.Utils

import java.util.*

object IdGenerator {

    fun generatePatientId(): String {
        val timestamp = System.currentTimeMillis()
        val random = (100000..999999).random()
        return "$timestamp$random"
    }

    fun generateShortId(): String {
        val uuid = UUID.randomUUID().toString().replace("-", "")
        return uuid.take(12) // first 12 chars of UUID
    }
}
