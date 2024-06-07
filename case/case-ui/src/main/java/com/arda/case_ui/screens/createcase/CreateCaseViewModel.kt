package com.arda.case_ui.screens.createcase

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import com.arda.case_api.domain.model.CaseLocation
import com.arda.case_api.domain.usecase.AddCaseUser
import com.arda.core_api.domain.usecase.GetMinimizedUserUseCase
import com.arda.core_api.util.DebugTagsEnumUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class CreateCaseViewModel @Inject constructor(
    private val getMinimizedUserUseCase: GetMinimizedUserUseCase,
    private val addCaseUser: AddCaseUser,
) : ViewModel(), LifecycleObserver {
    private val TAG = DebugTagsEnumUtils.UITag.tag

    private val _uiState = MutableStateFlow(CreateCaseUiState())
    val uiState: StateFlow<CreateCaseUiState> = _uiState.asStateFlow()
    fun onEvent(event: CreateCaseEvent) {
        when (event) {
            is CreateCaseEvent.addImage -> updateTakenImage(event.bitmap)
            is CreateCaseEvent.fillQRCode -> fillQRCode(event.locationString)
            is CreateCaseEvent.setHeader -> setHeader(event.header)
            is CreateCaseEvent.updateAddres -> TODO()
            is CreateCaseEvent.updateDescription -> TODO()
            is CreateCaseEvent.switchImagePopUp -> switchImagePopUp(value = event.bool)
        }
    }

    private fun setHeader(header: String) {
        _uiState.update {
            it.copy(selectedCategory = header)
        }
    }

    private fun updateTakenImage(value: Bitmap) {
        _uiState.update {
            it.copy(image = value)
        }
        Log.v(TAG, "CURRENT IMAGE : ${value}")
        switchImagePopUp(false)
    }

    private fun switchImagePopUp(value: Boolean) {
        _uiState.update {
            it.copy(imageShowPopUp = value)
        }
    }

    fun fillQRCode(locationString: String) {
        val locationList = locationString.split(";")
            .map { x -> x.strip() }// ex: İstanbul, Sarıyer, Rumelihisarı Mah. Hisarüstü Nispetiye Cad. No: 65;  ;Batmaz apt.;4
        _uiState.update {
            it.copy(
                location = CaseLocation(
                    address = locationList[0],
                    place = locationList[1],
                    building = locationList[2],
                    floor = locationList[3]
                )
            )
        }
        Log.v(TAG, "QR SUCEED! CURRENT LOC:${_uiState.value.location}")
    }
}