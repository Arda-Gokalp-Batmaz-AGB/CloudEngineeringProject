package com.arda.auth_ui.auth

import android.app.Activity
import com.arda.auth.auth_api.model.AuthTypeEnum

sealed class AuthEvent {
    data class changeAuthScreenState(val newState: AuthTypeEnum) : AuthEvent()
    object login : AuthEvent()
    object register : AuthEvent()
    data class getVerifyCode(val activity: Activity?): AuthEvent()
    data class setCountryCodeAndNumber(val countryCode: String, val countryPhoneCode: String): AuthEvent()
    data class updateEnteredPassword(val enteredValue: String): AuthEvent()
    data class updateEnteredEmail(val enteredValue: String): AuthEvent()
    data class updateEnteredPhoneNumber(val enteredValue: String): AuthEvent()
    data class updateEnteredVerifyNumber(val enteredValue: String): AuthEvent()
    data class handleUIErrors(val validationResultList: List<Exception>? = null) : AuthEvent()
    data class handleBackendErrors(val exception: Exception) : AuthEvent()
    data class setSubmitButton(val value: Boolean, ) : AuthEvent()
    data class validateErrors(val exception: Exception) : AuthEvent()
    object clearErrors : AuthEvent()
    object clearState : AuthEvent()
}