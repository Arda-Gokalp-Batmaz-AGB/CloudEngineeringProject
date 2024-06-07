package com.arda.profile_ui.screens.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.arda.core_api.domain.model.MinimizedUser
import com.arda.core_ui.nav.NavItem
import com.arda.core_ui.theme.ProjectTheme

@Composable
fun Profile(
    onEvent: (ProfileEvent) -> Unit,
    state: ProfileUiState,
    navController: NavHostController,
) {
    val currentUser by remember {
        mutableStateOf(state.currentUser)
    }
    Column(
        modifier = Modifier.fillMaxSize(1f),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconButton(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.3f)
                .clip(RoundedCornerShape(20.dp)),
            onClick = {
            }) {
            Icon(
                modifier = Modifier
                    .fillMaxSize(0.8f)
                    .clip(RoundedCornerShape(20.dp)),
                imageVector = Icons.Default.Person, contentDescription = null
            )
        }
        Text(text = currentUser!!.email, style = MaterialTheme.typography.headlineMedium)
        Text(text = currentUser!!.role, style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.fillMaxHeight(0.2f))
        Button(
            modifier = Modifier.fillMaxWidth(0.7f),
            onClick = {

                onEvent(ProfileEvent.logout)
                navController.navigate(NavItem.WaitScreen.route) {
                    popUpTo(NavItem.WaitScreen.route)
                }
                      },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
        ) {
            Text("Logout")
        }
    }
}

@Composable
@Preview
fun previewProfile() {
    ProjectTheme {
        Profile(
            onEvent = {}, state = ProfileUiState(
                currentUser = MinimizedUser(
                    uid = "dicant",
                    role = "dicunt",
                    email = "carey.hodge@example.com"
                )
            ), navController = rememberNavController()
        )
    }
}