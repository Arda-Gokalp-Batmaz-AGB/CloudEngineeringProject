package com.arda.cloudengineeringproject.core.components

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.os.Build
import android.view.View
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat

@Composable
fun HideSystemBars() {
    val view = LocalView.current
    val context = LocalContext.current

//    DisposableEffect(view) {
//                val windowInsetsController =
//            WindowCompat.getInsetsController(window, window.decorView)
    LaunchedEffect(key1 = Unit){
        val window = context.getActivity()?.window
        val windowInsetsController = if (Build.VERSION.SDK_INT >= 30) {
            WindowCompat.getInsetsController(window!!, view)
        } else {
            null
        }

        // Apply the changes when the composable is added
        if (Build.VERSION.SDK_INT >= 30) {
            windowInsetsController!!.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            windowInsetsController.hide(WindowInsetsCompat.Type.navigationBars())
        } else {
            @Suppress("DEPRECATION")
            view.systemUiVisibility = (View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        }
    }
}

// Helper function to get Activity from Context
fun Context.getActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.getActivity()
    else -> null
}