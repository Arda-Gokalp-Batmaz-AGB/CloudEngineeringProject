package com.arda.core_ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp
import com.arda.dystherapy.validation.ValidationResult

@Composable
fun ErrorText(validationResult: ValidationResult) {
    if (!validationResult.isValid) {
        validationResult.exception.message?.let {

        }
    }
    Text(
        text =
        if (!validationResult.isValid) {
            validationResult.exception.message ?: ""
        } else {
            ""
        },
        fontSize = 14.sp,
        color = MaterialTheme.colorScheme.error
    )
}