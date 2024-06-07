package com.arda.profile_ui.screens.profile

import com.arda.core_api.domain.model.MinimizedUser

data class ProfileUiState(
    val currentUser : MinimizedUser? = null,
) {
}