package com.arda.case_ui.screens.userhome

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arda.case_api.domain.usecase.GetCaseListByID
import com.arda.core_api.domain.model.MinimizedUser
import com.arda.core_api.domain.usecase.GetMinimizedUserUseCase
import com.arda.core_api.util.DebugTagsEnumUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserHomeViewModel @Inject constructor(
    private val getMinimizedUserUseCase: GetMinimizedUserUseCase,
    private val getCaseListByID: GetCaseListByID,
) : ViewModel(), LifecycleObserver  {
    private val _uiState = MutableStateFlow(UserHomeUiState())
    val uiState: StateFlow<UserHomeUiState> = _uiState.asStateFlow()

    private val currentUser: MinimizedUser?
        get() = getMinimizedUserUseCase()

    private val TAG = DebugTagsEnumUtils.UITag.tag

    init {
        listCases()
    }

    fun onEvent(event: UserHomeEvent) {
        when (event) {
            UserHomeEvent.listUserCases -> listCases()
            is UserHomeEvent.selectUserCase -> selectCase(event.caseID)
        }
    }

    fun selectCase(caseID: String) {
        _uiState.update {
            it.copy(selectedCaseID = caseID)
        }
    }

    fun listCases()  = viewModelScope.launch{
        getCaseListByID
    }
}