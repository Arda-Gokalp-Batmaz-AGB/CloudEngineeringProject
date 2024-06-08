package com.arda.case_ui.screens.casedetail

import android.graphics.Bitmap
import com.arda.case_api.domain.model.CaseProcessEnum
import com.arda.case_ui.screens.createcase.CreateCaseEvent

sealed class CaseDetailEvent {
    data class resolveCase(val result: CaseProcessEnum) : CaseDetailEvent()
    data class updateComment(val comment: String) : CaseDetailEvent()
    data class addImage(val bitmap : Bitmap) : CaseDetailEvent()
    data class switchImagePopUp(val bool : Boolean) : CaseDetailEvent()
    object submitComment: CaseDetailEvent()
}