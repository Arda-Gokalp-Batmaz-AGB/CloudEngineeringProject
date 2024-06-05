package com.arda.dystherapy.utils

import androidx.compose.runtime.ProvidableCompositionLocal

interface LoggedCheckProvider {
    val loggedCheckStateProvider: ProvidableCompositionLocal<Any>
}