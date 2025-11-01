package com.patientmanagementapp.Components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
//
//@Composable
//fun PatientTextField(
//    value: String,
//    onValueChange: (String) -> Unit,
//    label: String,
//    isPassword: Boolean = false,
//    modifier: Modifier = Modifier
//) {
//    OutlinedTextField(
//        value = value,
//        onValueChange = onValueChange,
//        label = { Text(label) },
//        modifier = modifier.fillMaxWidth(),
//        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None
//    )
//}

@Composable
fun PatientTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    isPassword: Boolean = false,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label, color = Color.Black) },
        singleLine = true,
        modifier = modifier,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.White,
            unfocusedBorderColor = Color.White.copy(alpha = 0.6f),
            focusedLabelColor = Color.Black,
            unfocusedLabelColor = Color.White.copy(alpha = 0.8f),
            cursorColor = Color.Black
        ),
        textStyle = LocalTextStyle.current.copy(color = Color.Black),
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None
    )
}
