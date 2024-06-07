package com.arda.profile_ui.screens.profile

sealed class ProfileEvent {
    object logout : ProfileEvent()
}