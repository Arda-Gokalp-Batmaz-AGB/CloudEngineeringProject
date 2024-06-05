package com.arda.dystherapy.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.smarttoolfactory.zoom.AnimatedZoomLayout

@Composable
fun MakeTextZoomable(isZoomable: Boolean, content: @Composable () -> Unit) {
    if (isZoomable)
        AnimatedZoomLayout(
            modifier = Modifier.fillMaxSize(),
            enabled = { zoom, _, _ ->
                (zoom > 1f)
            }
        ) {
            content()
        }
    if (isZoomable == false) {
        content()
    }
}