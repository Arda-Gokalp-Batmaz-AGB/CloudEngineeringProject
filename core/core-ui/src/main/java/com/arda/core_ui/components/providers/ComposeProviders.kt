package com.arda.core_ui.components.providers

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember

val LocalSnackbarHostState =
    compositionLocalOf<SnackbarHostState> { error("User State Context Not Found!") }

@Composable
fun ProvideSnackBar(content: @Composable () -> Unit) {
    val snackbarHostState = remember { SnackbarHostState() }
//    val LocalSnackbarHostState = compositionLocalOf<SnackbarHostState> {
//        error("No Snackbar Host State")
//    }
    CompositionLocalProvider(LocalSnackbarHostState provides snackbarHostState) {
        content()
    }
}