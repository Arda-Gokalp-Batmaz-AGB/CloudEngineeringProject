package com.arda.dystherapy.ui.auth

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.arda.auth_ui.auth.AuthEvent
import com.arda.dystherapy.components.country_code_picker.CountryPicker
import com.arda.dystherapy.components.country_code_picker.getListOfCountries
import com.arda.dystherapy.components.inputcomponents.GenericInputTextField
import com.arda.dystherapy.components.inputcomponents.GenericInputTextFieldWithTrailingIcon
import com.arda.dystherapy.model.AuthTypeEnum
import com.arda.dystherapy.util.DebugTagsEnumUtils
import com.arda.dystherapy.util.ResourceProvider
import com.arda.dystherapy.validation.StringResourceEnum

private val TAG = DebugTagsEnumUtils.UITag.tag

@Composable
fun PhoneAuthPage(onEvent: (AuthEvent) -> Unit, state: AuthUiState, navController: NavController) {
    if (state.currentAuthScreenState == AuthTypeEnum.LOGIN_WITH_PHONE) {
        PhoneLogin(onEvent, state, navController)
    }
}

@Composable
private fun PhoneLogin(onEvent: (AuthEvent) -> Unit, state: AuthUiState, navController: NavController) {
    GenericInputTextField(
        validationResult = state.phoneError,
        indicatorString = ResourceProvider(StringResourceEnum.PHONE),
        leadingIcon = {
            Card(
                modifier = Modifier
                    .width(100.dp)
                    .height(55.dp),
                shape = RoundedCornerShape(15.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondary)
            )
            {
                Box(contentAlignment = Alignment.Center)
                {
                    val countryCode = CountryPicker()
                    countryCode.CountryCodeDialog(
                        pickedCountry = {
                            onEvent(
                                AuthEvent.setCountryCodeAndNumber(
                                countryCode = it.countryCode,
                                countryPhoneCode = it.countryPhoneCode
                            ))

                            Log.v(
                                TAG, "country name is :  " +
                                        "${it.countryName} ${it.countryPhoneCode}"
                            )
                        },
                        defaultSelectedCountry = getListOfCountries().single { it.countryCode == state.selectedCountryCode },
                        dialogSearch = true,
                        dialogRounded = 22
                    )
                }

            }
        },
        enteredValue = state.enteredPhoneNumber,
        keyboardType = KeyboardType.Phone,
        valueChangeOperation = { newText ->
            run {
                if (newText.length <= 11) {
                    onEvent(AuthEvent.updateEnteredPhoneNumber(newText))
                }
            }
        }
    )
    GenericInputTextFieldWithTrailingIcon(
        validationResult = state.verifyCodeError,
        indicatorString = ResourceProvider(StringResourceEnum.VERIFY_CODE),
        leadingIcon = {
            Icon(
                Icons.Filled.PhoneAndroid,
                contentDescription = ResourceProvider(StringResourceEnum.EMAIL),
                modifier = Modifier.size(ButtonDefaults.IconSize)
            )
        },
        enteredValue = state.enteredVerifyCode,
        keyboardType = KeyboardType.Number,
        trailingIcon = { verifyButton(onEvent) },
        valueChangeOperation = { text -> if (text.length <= 6) onEvent(AuthEvent.updateEnteredVerifyNumber(text)) }
    )
    GenericSubmitButton(
        text = ResourceProvider(StringResourceEnum.LOGIN),
        onEvent = onEvent,
        state = state,
        type = AuthTypeEnum.LOGIN_WITH_PHONE,
        navController = navController
    )
    SwitchAuthType(
        newType = AuthTypeEnum.LOGIN_WITH_EMAIL,
        onEvent = onEvent,
        state = state,
        icon = Icons.Filled.Email,
        text = ResourceProvider(StringResourceEnum.LOGIN_W_EMAIL)
    )
}
internal fun Context.findActivity(): Activity {
    var context = this
    while (context is ContextWrapper) {
        if (context is Activity) return context
        context = context.baseContext
    }
    throw IllegalStateException("Permissions should be called in the context of an Activity")
}
@Composable
fun verifyButton(onEvent: (AuthEvent) -> Unit,) {
    val context = LocalContext.current
    val activity = context.findActivity()
    Button(
        modifier = Modifier
            .width(100.dp)
            .height(55.dp),
        shape = RoundedCornerShape(15.dp),
        onClick = { onEvent(AuthEvent.getVerifyCode(activity)) },
        enabled = CountTimeViewModel.isButtonActive,
        colors = ButtonDefaults.buttonColors(
            disabledContainerColor = Color.Magenta,
            containerColor = MaterialTheme.colorScheme.secondary
        )
    )
    {
        if (CountTimeViewModel.isButtonActive == false) {
            Text(text = "0:${CountTimeViewModel.remainingTime}")
        } else {
            Text(text = ResourceProvider(StringResourceEnum.GET_CODE))
        }
    }
}

