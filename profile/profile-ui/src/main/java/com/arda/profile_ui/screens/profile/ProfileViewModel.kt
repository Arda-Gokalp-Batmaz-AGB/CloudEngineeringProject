package com.arda.profile_ui.screens.profile

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import com.arda.auth.auth_api.usecase.LogoutUseCase
import com.arda.core_api.domain.model.MinimizedUser
import com.arda.core_api.domain.usecase.GetMinimizedUserUseCase
import com.arda.core_api.util.DebugTagsEnumUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getMinimizedUserUseCase: GetMinimizedUserUseCase,
    private val logoutUseCase: LogoutUseCase,
) : ViewModel(), LifecycleObserver {
    private val TAG = DebugTagsEnumUtils.UITag.tag
    private val _uiState = MutableStateFlow(ProfileUiState(currentUser = currentUser))
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()
    private val currentUser: MinimizedUser?
        get() = getMinimizedUserUseCase()

    fun onEvent(event: ProfileEvent) {
        when (event) {
            ProfileEvent.logout -> logout()
        }
    }

    fun logout() {
        logoutUseCase.invoke()
    }
}
