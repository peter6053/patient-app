package com.patientmanagementapp.Components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PatientRadioButton(
    selected: Boolean,
    onClick: () -> Unit,
    label: String
) {
    Row(modifier = Modifier.padding(8.dp)) {
        RadioButton(selected = selected, onClick = onClick)
        Text(
            text = label,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}