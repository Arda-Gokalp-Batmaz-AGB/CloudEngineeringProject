package com.arda.auth_ui.auth

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
import com.arda.auth.auth_api.model.AuthTypeEnum
import com.arda.core_ui.components.inputcomponents.GenericInputTextField
import com.arda.core_ui.components.inputcomponents.GenericInputTextFieldWithTrailingIcon

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
        indicatorString = "Email",
        leadingIcon = {
            Icon(
                Icons.Filled.Email,
                contentDescription = "Email",
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
        indicatorString = "Password",
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
        text = "Login",
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
        text = "Email"
    )
}

@Composable
private fun EmailRegister(onEvent: (AuthEvent) -> Unit, state: AuthUiState, navController: NavController) {
    GenericInputTextField(
        validationResult = state.emailError,
        indicatorString = "Email",
        leadingIcon = {
            Icon(
                Icons.Filled.Email,
                contentDescription = "Email",
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
        indicatorString = "Password",
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
        text = "Register",
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
        text = "Login with email"
    )
}


