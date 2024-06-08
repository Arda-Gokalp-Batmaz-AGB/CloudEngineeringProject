package com.arda.case_ui.screens.casedetail

import android.graphics.Bitmap
import com.arda.case_api.domain.model.Case
import com.arda.core_api.domain.model.MinimizedUser

data class CaseDetailUiState (
    val currentUser : MinimizedUser? = null,
    val loading : Boolean = false,
    val case: Case? = null,
    val comment : String = "",
    val image : Bitmap? = null,
    val imageShowPopUp : Boolean = false
)