package com.arda.case_ui.screens.createcase

import com.arda.case_api.domain.model.CaseLocation
import com.arda.case_api.domain.model.CategoryEnum

sealed class CreateCaseEvent {
    data class fillQRCode(val location: CaseLocation) : CreateCaseEvent()
    data class addImage(val image : String) : CreateCaseEvent()
    data class setHeader(val header : CategoryEnum) : CreateCaseEvent()
    data class updateDescription(val description : String) : CreateCaseEvent()
    data class updateAddres(val address : String) : CreateCaseEvent()
}