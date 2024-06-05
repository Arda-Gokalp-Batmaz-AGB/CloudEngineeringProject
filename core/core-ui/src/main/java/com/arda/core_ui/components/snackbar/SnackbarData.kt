package com.arda.dystherapy.components.snackbar

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarVisuals

class SnackbarData(
    override val message: String,
    val snackBarTypeEnum: SnackBarTypeEnum
) : SnackbarVisuals {
    override val actionLabel: String
        get() = ""
    override val withDismissAction: Boolean
        get() = false
    override val duration: SnackbarDuration
        get() = SnackbarDuration.Short
}