package com.arda.auth_ui.auth

import com.arda.auth.auth_api.model.AuthTypeEnum
import com.arda.core_api.domain.enums.RoleEnum
import com.arda.core_api.domain.model.MinimizedUser
import com.arda.core_api.util.Resource
import com.arda.core_api.validation.ValidationResult
import java.lang.Exception

data class AuthUiState (
    val currentAuthScreenState : AuthTypeEnum = AuthTypeEnum.LOGIN_WITH_EMAIL,
    val enteredPhoneNumber : String = "",
    val enteredPassword  : String = "",
    val enteredEmail  : String = "",
    val enteredVerifyCode  : String = "",
    val selectedCountryCode  : String = "gb",
    val selectedCountryNumber : String = "+44",

    val selectedRole : String = RoleEnum.user.toString(),
    val submitButtonOn  : Boolean = true,
    val authFlow: Resource<MinimizedUser>? = null,

    val passwordError: ValidationResult = DEFAULT_ERROR,
    val emailError: ValidationResult = DEFAULT_ERROR,
    var phoneError: ValidationResult = DEFAULT_ERROR,
    val verifyCodeError: ValidationResult = DEFAULT_ERROR,
    val backendError: ValidationResult = DEFAULT_ERROR,
)
{
    companion object {
        val DEFAULT_ERROR = ValidationResult(true, Exception())
    }
}
