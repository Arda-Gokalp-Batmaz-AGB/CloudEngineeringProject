package com.arda.core_ui.components.snackbar

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarVisuals
import com.arda.core_ui.components.snackbar.SnackBarTypeEnum

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