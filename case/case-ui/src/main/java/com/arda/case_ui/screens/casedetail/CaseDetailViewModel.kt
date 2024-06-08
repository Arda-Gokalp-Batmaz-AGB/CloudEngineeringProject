package com.arda.case_ui.screens.casedetail

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arda.case_api.domain.usecase.GetCaseByCaseID
import com.arda.core_api.domain.model.MinimizedUser
import com.arda.core_api.domain.usecase.GetMinimizedUserUseCase
import com.arda.core_api.util.DebugTagsEnumUtils
import com.arda.core_api.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CaseDetailViewModel @Inject constructor(
    private val getMinimizedUserUseCase: GetMinimizedUserUseCase,
    private val getCaseByCaseID: GetCaseByCaseID,
    savedStateHandle: SavedStateHandle,
) : ViewModel(), LifecycleObserver {
    private val TAG = DebugTagsEnumUtils.UITag.tag
    private val _uiState = MutableStateFlow(CaseDetailUiState(currentUser = currentUser))
    val uiState: StateFlow<CaseDetailUiState> = _uiState.asStateFlow()
    private val currentUser: MinimizedUser?
        get() = getMinimizedUserUseCase()
    private val caseID: String = savedStateHandle.get<String>("caseID") ?: ""

    init {
        getCase()
    }

    fun getCase() = viewModelScope.launch {
        val case = getCaseByCaseID(caseID)
        when (case) {
            is Resource.Failure<*> -> TODO()
            Resource.Loading -> TODO()
            is Resource.Sucess -> {
                _uiState.update {
                    it.copy(case = case.result)
                }
            }
        }
    }

    fun onEvent(event: CaseDetailEvent) {

    }
}
