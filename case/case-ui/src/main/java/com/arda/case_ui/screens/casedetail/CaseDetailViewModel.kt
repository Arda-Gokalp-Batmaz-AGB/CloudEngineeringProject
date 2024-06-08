package com.arda.case_ui.screens.casedetail

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arda.case_api.domain.model.CaseProcessEnum
import com.arda.case_api.domain.model.Comment
import com.arda.case_api.domain.usecase.AddCommentCase
import com.arda.case_api.domain.usecase.GetCaseByCaseID
import com.arda.case_api.domain.usecase.ResolveCaseOfficer
import com.arda.core_api.domain.model.MinimizedUser
import com.arda.core_api.domain.usecase.GetMinimizedUserUseCase
import com.arda.core_api.util.DebugTagsEnumUtils
import com.arda.core_api.util.Resource
import com.arda.core_api.util.copyOf
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
    private val addCommentCase: AddCommentCase,
    private val resolveCaseOfficer: ResolveCaseOfficer,
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
        when (event) {
            is CaseDetailEvent.resolveCase -> resolveCase(event.result)
            CaseDetailEvent.submitComment -> submitComment()
            is CaseDetailEvent.addImage -> updateTakenImage(event.bitmap)
            is CaseDetailEvent.switchImagePopUp -> switchImagePopUp(event.bool)
            is CaseDetailEvent.updateComment -> updateComment(event.comment)
        }
    }

    fun updateComment(text: String) {
        _uiState.update {
            it.copy(enteredComment = text)
        }
    }

    fun resolveCase(result: CaseProcessEnum) = viewModelScope.launch {
        resolveCaseOfficer(result.processName)
    }

    fun submitComment() = viewModelScope.launch {
        var result = addCommentCase(
            Comment(
                userID = currentUser!!.uid,
                userName = currentUser!!.email,
                caseID = caseID,
                text = _uiState.value.enteredComment,
                image = _uiState.value.commentImage?.toString()
            )
        )
        when (result) {
            is Resource.Failure<*> -> TODO()
            Resource.Loading -> TODO()
            is Resource.Sucess -> {
                val temp = _uiState.value.case!!.comments + listOf(result.result)
                _uiState.update {
                    it.copy(case = _uiState.value.case!!.copy(comments = temp.copyOf().toList()))
                }
            }
        }
    }

    private fun updateTakenImage(value: Bitmap) {
        _uiState.update {
            it.copy(commentImage = value)
        }
        Log.v(TAG, "CURRENT IMAGE : ${value}")
        switchImagePopUp(false)
    }

    private fun switchImagePopUp(value: Boolean) {
        _uiState.update {
            it.copy(imageShowPopUp = value)
        }
    }

}
