package com.arda.case_ui.screens.createcase

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arda.case_api.domain.model.Case
import com.arda.case_api.domain.model.CaseLocation
import com.arda.case_api.domain.model.CaseProcessEnum
import com.arda.case_api.domain.usecase.AddCaseUserUseCase
import com.arda.core_api.domain.model.MinimizedUser
import com.arda.core_api.domain.usecase.GetMinimizedUserUseCase
import com.arda.core_api.util.DebugTagsEnumUtils
import com.arda.core_ui.utils.ImageProcessUtils.BitmaptoBase64
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class CreateCaseViewModel @Inject constructor(
    private val getMinimizedUserUseCase: GetMinimizedUserUseCase,
    private val addCaseUserUseCase: AddCaseUserUseCase,
) : ViewModel(), LifecycleObserver {
    private val TAG = DebugTagsEnumUtils.UITag.tag
    private val _uiState = MutableStateFlow(CreateCaseUiState(currentUser=currentUser))
    val uiState: StateFlow<CreateCaseUiState> = _uiState.asStateFlow()
    private val currentUser: MinimizedUser?
        get() = getMinimizedUserUseCase()

    fun onEvent(event: CreateCaseEvent) {
        when (event) {
            is CreateCaseEvent.addImage -> updateTakenImage(event.bitmap)
            is CreateCaseEvent.fillQRCode -> fillQRCode(event.locationString)
            is CreateCaseEvent.setHeader -> setHeader(event.header)
            is CreateCaseEvent.updateAddres -> TODO()
            is CreateCaseEvent.updateDescription -> updateDescription(desc = event.description)
            is CreateCaseEvent.switchImagePopUp -> switchImagePopUp(value = event.bool)
            is CreateCaseEvent.submitForm -> submitForm(event.snackbarCall,event.navigate)
        }
    }

    fun submitForm(snackbarCall: () -> Job, navigate : () -> Unit) = viewModelScope.launch{
        addCaseUserUseCase(Case(
            id = UUID.randomUUID().toString(),
            userName = currentUser!!.email,
            assignedOfficerSubRole = null,//todo oto rol yada databaseden gelen cevaba göre
            currentProcess = CaseProcessEnum.waiting_for_response,
            image = uiState.value.image!!.BitmaptoBase64(),
            header = uiState.value.selectedCategory,
            time = LocalDate.now(),
            description = uiState.value.description,
            location = CaseLocation(
                address =  uiState.value.location!!.address,
                place =  uiState.value.location!!.place,
                building = uiState.value.location!!.building,
                floor = uiState.value.location!!.floor
            ),
            comments = listOf()
        ))
        snackbarCall.invoke().join()
        navigate.invoke()
    }
    private fun updateDescription(desc : String){
        _uiState.update {
            it.copy(description = desc)
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