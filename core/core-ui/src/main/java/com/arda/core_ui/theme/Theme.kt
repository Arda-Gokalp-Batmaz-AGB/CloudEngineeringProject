package com.arda.core_ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val colorPalette = lightColorScheme(
    primary = parent_primary,
    primaryContainer = parent_primary,
    secondary = parent_secondary,
    secondaryContainer = parent_secondary,
    tertiary = parent_tertiary,
    surface = parent_primary,
    onSurface = parent_tertiary,
    onPrimary = parent_on_primary,
    onSecondary = parent_on_secondary,
    error = parent_error,
    onError = parent_tertiary,
    background = parent_background,
    onBackground = parent_tertiary,
)

@Composable
fun ProjectTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit){
    MaterialTheme(
        colorScheme = colorPalette,
        typography = typography_parent,
        shapes = Shapes,
        content = content
    )
}
//@Composable
//fun ProjectPREVIEWTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit){
//    MaterialTheme(
//        colorScheme = colorPalette,
//        typography = typography_parent,
//        shapes = Shapes,
//        content = content
//    )
//}