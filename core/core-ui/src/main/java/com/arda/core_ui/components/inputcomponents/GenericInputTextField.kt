package com.arda.core_ui.components.inputcomponents

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.arda.core_api.validation.ValidationResult
import com.arda.core_ui.components.ErrorText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GenericInputTextField(
    enabled : Boolean = true,
    width: Dp = 300.dp,
    validationResult: ValidationResult,
    indicatorString: String,
    leadingIcon: @Composable () -> Unit = {},
    enteredValue: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    valueChangeOperation: (newText: String) -> Unit
) {
    Column(
        modifier = Modifier.wrapContentSize()
    ) {
        var color by remember { mutableStateOf(Color.Gray) }
        var errorType = !validationResult.isValid
        if (errorType) {
            color = MaterialTheme.colorScheme.error
        } else {
            color = MaterialTheme.colorScheme.secondary
        }
        var focused by remember { mutableStateOf(false) }
        TextField(
            modifier = Modifier
                .width(width)
                .border(2.dp, color, shape = RoundedCornerShape(15.dp))
                .background(
                    MaterialTheme.colorScheme.tertiary,
                    shape = RoundedCornerShape(15.dp)
                )
                .onFocusChanged {
                    focused = it.isFocused == true
                },
            isError = errorType,
            colors = TextFieldDefaults.colors(
                disabledTextColor = Color.Transparent,
//                focusedContainerColor = Color.Black,
//                unfocusedContainerColor = Color.Black,
//                disabledContainerColor = Color.Black,
                errorCursorColor = Color.Black,
                cursorColor = Color.Black,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent,
                errorLeadingIconColor = Color.Red,
            ),
            shape = RoundedCornerShape(15.dp),
            placeholder = { if (!focused) Text(indicatorString) },
            label = { Text((indicatorString), color = Color.Black) },
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            visualTransformation = visualTransformation,
            enabled = enabled,
            leadingIcon = {
                leadingIcon()
            },
            value = enteredValue, onValueChange = { newText ->
                valueChangeOperation(newText)
            })
        ErrorText(validationResult)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GenericInputTextFieldWithTrailingIcon(
    enabled : Boolean = true,
    width: Dp = 300.dp,
    validationResult: ValidationResult,
    indicatorString: String,
    leadingIcon: @Composable () -> Unit = {},
    enteredValue: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    trailingIcon: @Composable () -> Unit,
    valueChangeOperation: (newText: String) -> Unit
) {
    Column(
        modifier = Modifier.wrapContentSize()
    ) {
        var color by remember { mutableStateOf(Color.Gray) }
        var errorType = !validationResult.isValid
        if (errorType) {
            color = MaterialTheme.colorScheme.error
        } else {
            color = MaterialTheme.colorScheme.secondary
        }
        var focused by remember { mutableStateOf(false) }
        TextField(
            modifier = Modifier
                .width(width)
                .border(2.dp, color, shape = RoundedCornerShape(15.dp))
                .background(
                    MaterialTheme.colorScheme.tertiary,
                    shape = RoundedCornerShape(15.dp)
                )
                .onFocusChanged {
                    focused = it.isFocused == true
                },
            isError = errorType,
            colors = TextFieldDefaults.colors(
                disabledTextColor = Color.Transparent,
//                focusedContainerColor = Color.Black,
//                unfocusedContainerColor = Color.Black,
//                disabledContainerColor = Color.Black,
                errorCursorColor = Color.Black,
                cursorColor = Color.Black,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent,
                errorLeadingIconColor = Color.Red,
            ),
            shape = RoundedCornerShape(15.dp),
            placeholder = { if (!focused) Text(indicatorString) },
            label = { Text((indicatorString), color = Color.Black) },
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            visualTransformation = visualTransformation,//if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            leadingIcon = {
                leadingIcon()
            },
            trailingIcon = {
                trailingIcon()
            },
            value = enteredValue, onValueChange = { newText ->
                valueChangeOperation(newText)
            },
            enabled = enabled)


        ErrorText(validationResult)
    }
}