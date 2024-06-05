package com.arda.core_ui.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.core.content.ContextCompat

class PermissionsUtil() {
    private val cameraPermissions = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.CAMERA,
        Manifest.permission.INTERNET,
        Manifest.permission.ACCESS_WIFI_STATE,
        Manifest.permission.ACCESS_NETWORK_STATE
    )
    private val emptyPermissions = arrayOf<String>(Manifest.permission.ACCESS_NETWORK_STATE)
    fun checkAndRequestPermissions(
        context: Context,
        launcher: ManagedActivityResultLauncher<Array<String>, Map<String, Boolean>>,
        // type: InteractionItemEnum
    ) {
        var ps: Array<String>? = cameraPermissions + emptyPermissions
        if (ps != null) {
            if (
                ps.all {
                    ContextCompat.checkSelfPermission(
                        context,
                        it
                    ) == PackageManager.PERMISSION_GRANTED
                }
            ) {
                // Log.v("REPO", "ALL PERMISSIONS ARE GRANTED " + type)
                launcher.launch(ps)
            } else {
                launcher.launch(ps)
            }
        }
    }

    fun checkPerms(
        context: Context,
    ): Boolean {
        var ps: Array<String>? = null
        ps = cameraPermissions

        return ps.all {
            ContextCompat.checkSelfPermission(
                context,
                it
            ) == PackageManager.PERMISSION_GRANTED
        }
    }
}