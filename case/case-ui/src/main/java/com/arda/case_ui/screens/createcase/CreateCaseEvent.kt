package com.arda.case_ui.screens.createcase

import android.graphics.Bitmap
import com.arda.case_api.domain.model.CaseLocation
import com.arda.case_api.domain.model.CategoryEnum

sealed class CreateCaseEvent {
    data class fillQRCode(val locationString: String) : CreateCaseEvent()
    data class addImage(val bitmap : Bitmap) : CreateCaseEvent()
    data class switchImagePopUp(val bool : Boolean) : CreateCaseEvent()
    data class setHeader(val header : String) : CreateCaseEvent()
    data class updateDescription(val description : String) : CreateCaseEvent()
    data class updateAddres(val address : String) : CreateCaseEvent()
}