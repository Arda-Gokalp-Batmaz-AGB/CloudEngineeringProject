package com.arda.dystherapy.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

//private val DarkColorPalette = darkColorScheme(
//    primary = backgroundColor,
//    secondary = menuColor,
//    tertiary = textColor,
//    surface = Orange,
//    onSecondary = Peach,
//    error = Color.Red,
////    onSecondaryContainer = qColor,
//    background = backgroundColor,
//)

private val LightColorPalette = lightColorScheme(
    primary = backgroundColor,
    secondary = menuColor,
    tertiary = parent_tertiary,
    surface = Peach,
    onSecondary = parent_tertiary,
    error = child_error,
//    onSecondaryContainer = qColor,
    background = backgroundColor,
    primaryContainer = child_button_color,
    onPrimaryContainer = parent_tertiary,
    secondaryContainer = menuColor,
    onSecondaryContainer = parent_tertiary
    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

private val parentCollorPalette = lightColorScheme(
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

//@Composable
//fun DysThearapyParentTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit){
//    MaterialTheme(
//        colorScheme = parentCollorPalette,
//        typography = Typography,
//        shapes = Shapes,
//        content = content
//    )
//}
//@Composable
//fun DystherapyChildTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit){
//    val colors = if (darkTheme) {
//        DarkColorPalette
//    } else {
//        LightColorPalette
//    }
//
//    MaterialTheme(
//        colorScheme = colors,
//        typography = Typography,
//        shapes = Shapes,
//        content = content
//    )
//}
@Composable
fun DystherapyTheme(theme: Theme,darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit){

    when(theme){
        Theme.CHILD_THEME -> {
            val colors = if (darkTheme) {
                LightColorPalette
//                DarkColorPalette
            } else {
                LightColorPalette
            }

            MaterialTheme(
                colorScheme = colors,
                typography = typography_child,
                shapes = Shapes,
                content = content
            )
        }
        Theme.PARENT_THEME ->{
            MaterialTheme(
                colorScheme = parentCollorPalette,
                typography = typography_parent,
                shapes = Shapes,
                content = content
            )
        }
    }

    val view = LocalView.current
    DisposableEffect(theme) {
        val activity = view.context as Activity
        when(theme){
            Theme.CHILD_THEME -> {
                activity.window.statusBarColor = LightColorPalette.background.toArgb()
//                activity.window.navigationBarColor = LightColorPalette.background.toArgb()
            }
            Theme.PARENT_THEME -> {
                activity.window.statusBarColor = parentCollorPalette.background.toArgb()
//                activity.window.navigationBarColor = parentCollorPalette.background.toArgb()
            }
        }
//        activity.window.statusBarColor = if(isDarkMod){statusBarDark} else {statusBarLight}
//        activity.window.navigationBarColor = if(isDarkMod){navigationBarDark} else {navigationBarLight}

//        WindowCompat.getInsetsController(activity.window, activity.window.decorView).apply {
//            isAppearanceLightStatusBars = !isDarkMod
//            isAppearanceLightNavigationBars = !isDarkMod
//        }

        onDispose { }
    }

}
enum class Theme{
    CHILD_THEME,
    PARENT_THEME
}

@Composable
fun DystherapyPREVIEWTheme(theme: Theme,darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit){
    when(theme){
        Theme.CHILD_THEME -> {
            val colors = if (darkTheme) {
                LightColorPalette
//                DarkColorPalette
            } else {
                LightColorPalette
            }

            MaterialTheme(
                colorScheme = colors,
                typography = typography_child,
                shapes = Shapes,
                content = content
            )
        }
        Theme.PARENT_THEME ->{
            MaterialTheme(
                colorScheme = parentCollorPalette,
                typography = typography_parent,
                shapes = Shapes,
                content = content
            )
        }
    }
}