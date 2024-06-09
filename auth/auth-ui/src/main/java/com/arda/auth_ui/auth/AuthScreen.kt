package com.arda.auth_ui.auth

import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.ActivityInfo
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Login
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.arda.auth.auth_api.model.AuthTypeEnum
import com.arda.core_api.util.DebugTagsEnumUtils
import com.arda.core_api.util.Resource
import com.arda.core_ui.nav.NavItem

private val TAG = DebugTagsEnumUtils.UITag.tag

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AuthScreen(
    onEvent: (AuthEvent) -> Unit,
    state: AuthUiState,
    navController: NavController) {
    val activity = LocalContext.current as Activity
    LaunchedEffect(Unit) {
        activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        onEvent(AuthEvent.changeAuthScreenState(AuthTypeEnum.LOGIN_WITH_EMAIL))
    }
    Scaffold()
    {
        Box(
            modifier = Modifier
                .fillMaxWidth(1f)
                .fillMaxHeight(1f),
            contentAlignment = Alignment.Center
        )
        {
            Column(
                modifier = Modifier
                    .matchParentSize()
                    .verticalScroll(rememberScrollState())
                    .padding(top = 30.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(15.dp)
            ) {
                ImageLogo()
                AuthLogic(onEvent,state, navController)
            }
        }
    }
}

@Composable
fun AuthLogic(
    onEvent: (AuthEvent) -> Unit,
    state: AuthUiState,
    navController: NavController) {
    val currentAuthScreenState by rememberUpdatedState(newValue = state.currentAuthScreenState)
    if (currentAuthScreenState == AuthTypeEnum.LOGIN_WITH_EMAIL || currentAuthScreenState == AuthTypeEnum.REGISTER_WITH_EMAIL) {
        EmailAuthPage(currentAuthScreenState,onEvent, state,navController)
    }
}

@Composable
fun ImageLogo() {
    Card(
        modifier = Modifier
            .width(200.dp)
            .height(200.dp)
            .clip(CircleShape),
        shape = RoundedCornerShape(15.dp),
        elevation = CardDefaults.cardElevation(5.dp)
    )
    {
        Image(
            modifier = Modifier
                .fillMaxSize(),
            painter = painterResource(R.drawable.splash_icon),
            contentScale = ContentScale.Crop,
            contentDescription = "Logo"
        )
    }

}

@Composable
fun SwitchAuthType(
    newType: AuthTypeEnum,
    onEvent: (AuthEvent) -> Unit,
    state: AuthUiState,
    icon: ImageVector,
    text: String,
) {
    Button(
        modifier = Modifier
            .width(300.dp),
        shape = RoundedCornerShape(15.dp),
        enabled = state.submitButtonOn,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.secondary
        ),
        onClick = {
            onEvent(AuthEvent.changeAuthScreenState(newType))
            Log.d(
                DebugTagsEnumUtils.UITag.tag,
                "Current state changed to${state.currentAuthScreenState.toString()}"
            )
        })
    {
        Icon(
            icon,
            contentDescription = "",
            modifier = Modifier.size(ButtonDefaults.IconSize),
            tint = MaterialTheme.colorScheme.onSecondary
        )
        Text(text = text, color = MaterialTheme.colorScheme.onSecondary)
    }
}

@Composable
fun GenericSubmitButton(
    text: String,
    onEvent: (AuthEvent) -> Unit,
    state: AuthUiState,
    type: AuthTypeEnum,
    navController: NavController
) {
    val mContext = LocalContext.current
    Button(
        modifier = Modifier
            .width(300.dp),
        shape = RoundedCornerShape(15.dp),
        enabled = state.submitButtonOn,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.secondary
        ),
        onClick = {
            Log.v(TAG, "Login/Register is triggered")
            onEvent(AuthEvent.clearErrors)

            when(type){
                AuthTypeEnum.LOGIN_WITH_EMAIL ->  onEvent(AuthEvent.login)
                AuthTypeEnum.REGISTER_WITH_EMAIL -> onEvent(AuthEvent.register)
                else -> {return@Button}
            }
        }) {
        Text(text = text, color = MaterialTheme.colorScheme.onSecondary)
        Icon(
            Icons.Filled.Login,
            contentDescription = "",
            modifier = Modifier.size(ButtonDefaults.IconSize),
            tint = MaterialTheme.colorScheme.onSecondary
        )
    }
    Spacer(modifier = Modifier.height(20.dp))
    state.authFlow?.toString()?.let { Log.v(TAG, "Login Flow: " + it) }

//    val loggedCheckViewModel = loggedCheckState.current
//    if(state.authFlow != null)
//        loggedCheckState.current.setLoginState(state.authFlow)

    state.authFlow?.let {
        when (it) {
            is Resource.Failure<*> -> {
                LaunchedEffect(Unit)
                {
                    if (!state.submitButtonOn) {
                        val exception = it.exception
                        if (exception == null)//UI error
                        {
                            onEvent(AuthEvent.handleUIErrors(it.exceptionList))
                        } else //SERVER ERROR
                        {
                            onEvent(AuthEvent.handleBackendErrors(exception!!))
                            Toast.makeText(
                                mContext,
                                exception.message,
                                Toast.LENGTH_LONG
                            ).show()
                        }
                        onEvent(AuthEvent.setSubmitButton(true))
                    }
                }
            }
            is Resource.Loading -> {
                Log.v(TAG, "AUTH IS LOADING ")
                onEvent(AuthEvent.setSubmitButton(false))

                CircularProgressIndicator()
            }
            is Resource.Sucess -> {
                LaunchedEffect(Unit)
                {
                    onEvent(AuthEvent.setSubmitButton(true))
                    Toast.makeText(
                        mContext,
                        "Login Sucess",
                        Toast.LENGTH_LONG
                    ).show()
                    Log.v(DebugTagsEnumUtils.UITag.tag, "LOGIN SUCESS! ROUTE TO HOME")
                    onEvent(AuthEvent.clearState)
//                    loggedCheckViewModel.callInit()
                    navController.navigate(NavItem.WaitScreen.route){
                        popUpTo(NavItem.WaitScreen.route)
                    }
                }

            }
        }
    }
//    ErrorText(validationResult = state.backendError)
}
