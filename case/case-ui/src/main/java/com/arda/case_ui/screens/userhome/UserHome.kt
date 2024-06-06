package com.arda.case_ui.screens.userhome

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun UserHome(
    onEvent: (UserHomeEvent) -> Unit,
    state: UserHomeUiState,
    navController: NavController
) {
    Text("Home")
}

@Preview
@Composable
fun previewUserHome(){
    UserHome(
        onEvent = {}, state = UserHomeUiState(
            currentUser = null,
            selectedCaseID = "",
            caseList = listOf()
        ), navController = rememberNavController()
    )
}