package com.arda.case_ui.screens.createcase

import android.graphics.Bitmap
import com.arda.case_api.domain.model.CaseLocation
import com.arda.case_api.domain.model.CategoryEnum
import com.arda.core_api.domain.model.MinimizedUser

data class CreateCaseUiState (
    val currentUser : MinimizedUser? = null,
    val selectedCategory : String = CategoryEnum.empty.categoryName,
    val addressText : String = "",
    val description : String = "",
    val location: CaseLocation? = CaseLocation(
        address = "İstanbul, Sarıyer, Rumelihisarı Mah. Hisarüstü Nispetiye Cad. No: 65",
        place = "",
        building = "Batmaz apt.",
        floor = "5"
    ), //İstanbul, Sarıyer, Rumelihisarı Mah. Hisarüstü Nispetiye Cad. No: 65;  ;Batmaz apt.;
    val categoryEnum: CategoryEnum = CategoryEnum.empty,
    val image : Bitmap? = null,
    val imageShowPopUp : Boolean = false
)