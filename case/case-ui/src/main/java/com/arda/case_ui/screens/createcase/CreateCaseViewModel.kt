package com.arda.case_ui.screens.createcase

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import com.arda.case_api.domain.usecase.AddCaseUser
import com.arda.core_api.domain.usecase.GetMinimizedUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class CreateCaseViewModel @Inject constructor(
    private val getMinimizedUserUseCase: GetMinimizedUserUseCase,
    private val addCaseUser: AddCaseUser
) : ViewModel(), LifecycleObserver {
    private val _uiState = MutableStateFlow(CreateCaseUiState())
    val uiState: StateFlow<CreateCaseUiState> = _uiState.asStateFlow()
    fun onEvent(event: CreateCaseEvent){
        when(event){
            is CreateCaseEvent.addImage -> TODO()
            is CreateCaseEvent.fillQRCode -> TODO()
            is CreateCaseEvent.setHeader -> TODO()
            is CreateCaseEvent.updateAddres -> TODO()
            is CreateCaseEvent.updateDescription -> TODO()
        }
    }
}