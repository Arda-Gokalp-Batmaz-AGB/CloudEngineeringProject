package com.arda.case_ui.screens.createcase

import com.arda.case_api.domain.model.CaseLocation
import com.arda.case_api.domain.model.CategoryEnum
import com.arda.core_api.domain.model.MinimizedUser

data class CreateCaseUiState (
    val currentUser : MinimizedUser? = null,
    val addressText : String = "",
    val description : String = "",
    val location: CaseLocation? = null,
    val categoryEnum: CategoryEnum = CategoryEnum.empty,
    val image : String = ""
)