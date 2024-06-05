package com.arda.auth_ui.auth

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.telephony.TelephonyManager
import android.util.Log
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arda.auth.auth_api.model.AuthTypeEnum
import com.arda.auth.auth_api.usecase.LoginUseCase
import com.arda.auth.auth_api.usecase.RegisterUseCase
import com.arda.core_api.domain.model.MinimizedUser
import com.arda.core_api.domain.usecase.GetMinimizedUserUseCase
import com.arda.core_api.util.DebugTagsEnumUtils
import com.arda.core_api.util.Resource
import com.arda.core_api.validation.ValidationResult
import com.arda.core_api.validation.ValidationResultEnum
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val registerUseCase: RegisterUseCase,
    private val getMinimizedUserUseCase: GetMinimizedUserUseCase
) : ViewModel(), LifecycleObserver {
    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    private val currentUser: MinimizedUser?
        get() = getMinimizedUserUseCase()

    private val TAG = DebugTagsEnumUtils.UITag.tag

    init {
        Log.v(TAG, "current USER: " + currentUser.toString())
        if (currentUser != null) {
            _uiState.update {
                it.copy(authFlow = Resource.Sucess(currentUser!!))
            }
        }
    }

    fun onEvent(event: AuthEvent){
        when(event){
            is AuthEvent.changeAuthScreenState -> changeAuthScreenState(event.newState)
            AuthEvent.clearErrors -> clearErrors()
            AuthEvent.clearState -> clearState()
            is AuthEvent.handleBackendErrors -> handleBackendErrors(event.exception)
            is AuthEvent.handleUIErrors -> handleUIErrors(event.validationResultList)
            AuthEvent.login -> login()
            AuthEvent.register -> register()
            is AuthEvent.setSubmitButton -> setSubmitButton(event.value)
            is AuthEvent.updateEnteredEmail -> updateEnteredEmail(event.enteredValue)
            is AuthEvent.updateEnteredPassword -> updateEnteredPassword(event.enteredValue)
            is AuthEvent.updateEnteredPhoneNumber -> updateEnteredPhoneNumber(event.enteredValue)
            is AuthEvent.updateEnteredVerifyNumber -> updateEnteredVerifyNumber(event.enteredValue)
            is AuthEvent.validateErrors -> validateErrors(event.exception)
        }
    }
    fun changeAuthScreenState(newState: AuthTypeEnum) {
        _uiState.update {
            it.copy(currentAuthScreenState = newState)
        }
    }

    fun login() = viewModelScope.launch {
        _uiState.update {
            it.copy(authFlow = Resource.Loading)
        }
        Log.v(TAG,"Current value of authflow : ${_uiState.value.authFlow}")
        val result = loginUseCase(
            email = uiState.value.enteredEmail,
            password = uiState.value.enteredPassword,
            verifyCode = uiState.value.enteredVerifyCode,
            authType = uiState.value.currentAuthScreenState
        )
        delay(1000)
        _uiState.update {
            it.copy(authFlow = result)
        }
        Log.v(TAG, result.toString())
    }

    fun register() = viewModelScope.launch {
        _uiState.update {
            it.copy(authFlow = Resource.Loading)
        }
        val result = registerUseCase(
            email = uiState.value.enteredEmail,
            password = uiState.value.enteredPassword,
            authType = uiState.value.currentAuthScreenState
        )
        delay(1000)
        _uiState.update {
            it.copy(authFlow = result)
        }
        Log.v(TAG, result.toString())
    }

    fun updateEnteredPassword(enteredValue: String) {
        _uiState.update {
            it.copy(enteredPassword = enteredValue)
        }
    }

    fun updateEnteredEmail(enteredValue: String) {
        _uiState.update {
            it.copy(enteredEmail = enteredValue)
        }
    }

    fun updateEnteredPhoneNumber(enteredValue: String) {
        _uiState.update {
            it.copy(enteredPhoneNumber = enteredValue)
        }
    }

    fun updateEnteredVerifyNumber(enteredValue: String) {
        _uiState.update {
            it.copy(enteredVerifyCode = enteredValue)
        }
    }

    fun handleUIErrors(validationResultList: List<Exception>? = null)
    {
        if (!validationResultList.isNullOrEmpty())
            validationResultList.forEach { x ->
                validateErrors(x)
            }
    }
    fun handleBackendErrors(exception: Exception)
    {
        _uiState.update {
            it.copy(
                backendError = ValidationResult(
                    isValid = false,
                    exception = exception
                ),
            )
        }
    }
    fun setSubmitButton(value: Boolean, ) {
        _uiState.update {
            it.copy(submitButtonOn = value)
        }
    }

    fun validateErrors(exception: Exception) {
        val message = exception.message
        Log.v(TAG,"T:${message}")
        val errorEnum = ValidationResultEnum.valueOf(message!!.substringBefore("="))
        val errorMessage = message!!.substringAfter("=")
        when (errorEnum) {
            ValidationResultEnum.UI_EMAIL_ERROR -> {
                _uiState.update {
                    it.copy(
                        emailError = ValidationResult(
                            isValid = false,
                            exception = Exception(errorMessage)
                        ),
                    )
                }
            }
            ValidationResultEnum.UI_PASSWORD_ERROR ->{
                _uiState.update {
                    it.copy(
                        passwordError = ValidationResult(
                            isValid = false,
                            exception = Exception(errorMessage)
                        ),
                    )
                }
            }
            ValidationResultEnum.UI_PHONE_ERROR -> {
                _uiState.update {
                    it.copy(
                        phoneError = ValidationResult(
                            isValid = false,
                            exception = Exception(errorMessage)
                        ),
                    )
                }
            }
            ValidationResultEnum.UI_VERIFY_CODE_ERROR -> {
                _uiState.update {
                    it.copy(
                        verifyCodeError = ValidationResult(
                            isValid = false,
                            exception = Exception(errorMessage)
                        ),
                    )
                }
            }
            else ->{return}
        }


    }

    fun clearErrors() {
        _uiState.update {
            it.copy(emailError = AuthUiState.DEFAULT_ERROR)
        }
        _uiState.update {
            it.copy(passwordError = AuthUiState.DEFAULT_ERROR)
        }
        _uiState.update {
            it.copy(phoneError = AuthUiState.DEFAULT_ERROR)
        }
        _uiState.update {
            it.copy(verifyCodeError = AuthUiState.DEFAULT_ERROR)
        }
        _uiState.update {
            it.copy(backendError = AuthUiState.DEFAULT_ERROR)
        }
    }

    fun clearState() {
        _uiState.update { AuthUiState() }
    }
}
