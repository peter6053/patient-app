package com.patientmanagementapp.Utils

sealed class Resource<T>(
    val data: T? = null,
    var message: String = ""
) {
    class Error<T>(message: String) : Resource<T>(message = message)
    class Success<T>(data: T?) : Resource<T>(data = data)
    class Loading<T> : Resource<T>()
}