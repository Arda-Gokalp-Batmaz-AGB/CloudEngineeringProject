package com.arda.case_ui.screens.userhome

import com.arda.case_api.domain.model.Case
import com.arda.core_api.domain.model.MinimizedUser


data class UserHomeUiState (
    val currentUser : MinimizedUser? = null,
    val selectedCaseID : String = "",
    val isLoading : Boolean = false,
    val caseList : List<Case> = listOf()
)