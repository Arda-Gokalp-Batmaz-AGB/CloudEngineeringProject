package com.arda.dystherapy.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.core.content.ContextCompat

class PermissionsUtil() {
    private val speakPermissions = arrayOf(
        Manifest.permission.RECORD_AUDIO,
        Manifest.permission.INTERNET,
        Manifest.permission.ACCESS_WIFI_STATE,
        Manifest.permission.ACCESS_NETWORK_STATE
    )
    private val voicePermissions = arrayOf(
        Manifest.permission.ACCESS_NETWORK_STATE,
        Manifest.permission.INTERNET,
        Manifest.permission.ACCESS_WIFI_STATE,
        Manifest.permission.ACCESS_NETWORK_STATE
    )
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
        var ps: Array<String>? =
            speakPermissions + voicePermissions + cameraPermissions + emptyPermissions
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
        types: List<InteractionItemEnum>
    ): Boolean {
        for (type in types) {
            var ps: Array<String>? = null
            if (type == InteractionItemEnum.SPEAK) {
                ps = speakPermissions
            } else if (type == InteractionItemEnum.LISTEN) {
                ps = voicePermissions
            } else if (type == InteractionItemEnum.CAMERA) {
                ps = cameraPermissions
            } else {
                ps = emptyPermissions
            }
            if (ps != null &&
                ps.all {
                    ContextCompat.checkSelfPermission(
                        context,
                        it
                    ) == PackageManager.PERMISSION_GRANTED
                }
            ) {
                continue
            } else {
                return false
            }
        }
        return true
    }
}