package com.arda.cloudengineeringproject.core.components

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun BackButtonHandler(onNavigateBack : () -> Unit ) {// speakerModel: SpeakerModel,
    BackHandler(enabled = true, onBack = onNavigateBack)
}