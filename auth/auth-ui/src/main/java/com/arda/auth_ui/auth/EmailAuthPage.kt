package com.arda.dystherapy.ui.auth

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.navigation.NavController
import com.arda.auth_ui.auth.AuthEvent
import com.arda.dystherapy.components.inputcomponents.GenericInputTextField
import com.arda.dystherapy.components.inputcomponents.GenericInputTextFieldWithTrailingIcon
import com.arda.dystherapy.util.ResourceProvider
import com.arda.dystherapy.validation.StringResourceEnum
import com.arda.dystherapy.model.AuthTypeEnum

@Composable
fun EmailAuthPage(currentAuthScreenState : AuthTypeEnum, onEvent: (AuthEvent) -> Unit, state: AuthUiState, navController: NavController) {
    if (currentAuthScreenState == AuthTypeEnum.LOGIN_WITH_EMAIL) {
        EmailLogin(onEvent, state, navController)
    } else {
        EmailRegister(onEvent, state, navController)
    }
}

@Composable
private fun EmailLogin(onEvent: (AuthEvent) -> Unit, state: AuthUiState, navController: NavController) {
    GenericInputTextField(
        validationResult = state.emailError,
        indicatorString = ResourceProvider(StringResourceEnum.EMAIL),
        leadingIcon = {
            Icon(
                Icons.Filled.Email,
                contentDescription = ResourceProvider(StringResourceEnum.EMAIL),
                modifier = Modifier.size(ButtonDefaults.IconSize)
            )
        },
        enteredValue = state.enteredEmail,
        keyboardType = KeyboardType.Email,
        valueChangeOperation = { text ->
            onEvent(AuthEvent.updateEnteredEmail(text))
        }
    )
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    GenericInputTextFieldWithTrailingIcon(
        validationResult = state.passwordError,
        indicatorString = ResourceProvider(StringResourceEnum.PASSWORD),
        leadingIcon = {
            Icon(
                Icons.Filled.Lock,
                contentDescription = "",
                modifier = Modifier.size(ButtonDefaults.IconSize),
            )
        },
        trailingIcon = {
            val image = if (passwordVisible)
                Icons.Filled.Visibility
            else Icons.Filled.VisibilityOff

            val description = if (passwordVisible) "Hide password" else "Show password"

            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                Icon(imageVector = image, description)
            }
        },
        keyboardType = KeyboardType.Password,
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        enteredValue = state.enteredPassword,
        valueChangeOperation = {onEvent(AuthEvent.updateEnteredPassword(it))}
    )
    GenericSubmitButton(
        text = ResourceProvider(StringResourceEnum.LOGIN),
        onEvent = onEvent,
        state = state,
        type = AuthTypeEnum.LOGIN_WITH_EMAIL,
        navController = navController
    )
    SwitchAuthType(
        newType = AuthTypeEnum.REGISTER_WITH_EMAIL,
        onEvent = onEvent,
        state = state,
        icon = Icons.Filled.Email,
        text = ResourceProvider(StringResourceEnum.REGISTER_W_EMAIL)
    )
    SwitchAuthType(
        newType = AuthTypeEnum.LOGIN_WITH_PHONE,
        onEvent = onEvent,
        state = state,
        icon = Icons.Filled.PhoneAndroid,
        text = ResourceProvider(StringResourceEnum.LOGIN_W_PHONE)
    )
}

@Composable
private fun EmailRegister(onEvent: (AuthEvent) -> Unit, state: AuthUiState, navController: NavController) {
    GenericInputTextField(
        validationResult = state.emailError,
        indicatorString = ResourceProvider(StringResourceEnum.EMAIL),
        leadingIcon = {
            Icon(
                Icons.Filled.Email,
                contentDescription = ResourceProvider(StringResourceEnum.EMAIL),
                modifier = Modifier.size(ButtonDefaults.IconSize)
            )
        },
        enteredValue = state.enteredEmail,
        keyboardType = KeyboardType.Email,
        valueChangeOperation = { text ->
            onEvent(AuthEvent.updateEnteredEmail(text))
        }
    )

    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    GenericInputTextFieldWithTrailingIcon(
        validationResult = state.passwordError,
        indicatorString = ResourceProvider(StringResourceEnum.PASSWORD),
        leadingIcon = {
            Icon(
                Icons.Filled.Lock,
                contentDescription = "",
                modifier = Modifier.size(ButtonDefaults.IconSize),
            )
        },
        trailingIcon = {
            val image = if (passwordVisible)
                Icons.Filled.Visibility
            else Icons.Filled.VisibilityOff

            val description = if (passwordVisible) "Hide password" else "Show password"

            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                Icon(imageVector = image, description)
            }
        },
        keyboardType = KeyboardType.Password,
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        enteredValue = state.enteredPassword,
        valueChangeOperation = {onEvent(AuthEvent.updateEnteredPassword(it))}
    )

    GenericSubmitButton(
        text = ResourceProvider(StringResourceEnum.REGISTER),
        onEvent = onEvent,
        state = state,
        type = AuthTypeEnum.REGISTER_WITH_EMAIL,
        navController = navController
    )
    SwitchAuthType(
        newType = AuthTypeEnum.LOGIN_WITH_EMAIL,
        onEvent = onEvent,
        state = state,
        icon = Icons.Filled.Email,
        text = ResourceProvider(StringResourceEnum.LOGIN_W_EMAIL)
    )
    SwitchAuthType(
        newType = AuthTypeEnum.LOGIN_WITH_PHONE,
        onEvent = onEvent,
        state = state,
        icon = Icons.Filled.PhoneAndroid,
        text = ResourceProvider(StringResourceEnum.LOGIN_W_PHONE)
    )
}


