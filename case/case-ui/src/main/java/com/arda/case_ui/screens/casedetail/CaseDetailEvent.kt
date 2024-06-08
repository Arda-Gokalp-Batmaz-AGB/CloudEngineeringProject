package com.arda.case_ui.screens.casedetail

import com.arda.case_api.domain.model.CaseProcessEnum

sealed class CaseDetailEvent {
    data class resolveCase(val result: CaseProcessEnum) : CaseDetailEvent()
    object submitComment: CaseDetailEvent()
}