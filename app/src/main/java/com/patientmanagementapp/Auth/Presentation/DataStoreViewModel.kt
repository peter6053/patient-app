package com.patientmanagementapp.Auth.Presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.patientmanagementapp.Utils.DataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DataStoreViewModel @Inject constructor(
    val dataStoreManager: DataStoreManager
) : ViewModel() {

    // Expose the access token as StateFlow for Compose
    val accessTokenFlow = dataStoreManager.accessTokenFlow

    // Save the access token
    fun saveAccessToken(token: String) {
        viewModelScope.launch {
            dataStoreManager.saveAccessToken(token)
        }
    }

    // Clear all stored data
    fun clearDataStore() {
        viewModelScope.launch {
            dataStoreManager.clear()
        }
    }
}
