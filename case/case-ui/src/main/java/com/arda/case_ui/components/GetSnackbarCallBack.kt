package com.arda.case_ui.components

import android.util.Log
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.SnackbarVisuals
import com.arda.core_api.util.DebugTagsEnumUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
private val TAG = DebugTagsEnumUtils.UITag.tag
class SnackbarData(
    override val message: String,
) : SnackbarVisuals {
    override val actionLabel: String
        get() = ""
    override val withDismissAction: Boolean
        get() = false
    override val duration: SnackbarDuration
        get() = SnackbarDuration.Short
}
fun GetSnackBarChoiceCallBack(
    snackbarHostState: SnackbarHostState,
    message : String,
    scope: CoroutineScope,
): () -> Job {
    return {
        scope.launch {
            snackbarHostState.currentSnackbarData?.dismiss()
            Log.v(TAG, "Choice Snackbar Triggered : ${snackbarHostState.currentSnackbarData}")
            val snackResult =
                snackbarHostState.showSnackbar(
                    SnackbarData(
                        message = message
                    )
                )
            when (snackResult) {
                SnackbarResult.ActionPerformed -> {
                    Log.v(TAG, "Choice Snackbar Action performed")
                }

                SnackbarResult.Dismissed -> {
                    Log.v(TAG, "Choice Snackbar dismissed")
                }

                else -> {}
            }
        }
    }
}