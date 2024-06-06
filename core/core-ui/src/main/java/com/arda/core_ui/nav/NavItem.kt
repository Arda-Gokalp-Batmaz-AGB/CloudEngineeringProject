package com.arda.core_ui.nav

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ConnectedTv
import androidx.compose.material.icons.filled.HistoryEdu
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Monitor
import androidx.compose.material.icons.filled.Person4
import androidx.compose.ui.graphics.vector.ImageVector


sealed class NavItem(
    val route: String,
    val icon: ImageVector
) {
    abstract val title: String

    object WaitScreen : NavItem(
        route = "wait",
        icon = Icons.Filled.Key
    )
    {
        override val title: String = "Wait"
    }
    object Auth : NavItem(
        route = "auth",
        icon = Icons.Filled.Key
    ) {
        override val title: String = "Auth"
    }
    object Home : NavItem(
        route = "home",
        icon = Icons.Filled.Home
    ) {
        override val title: String = "Home"
    }
    object NewCase : NavItem(
        route = "newcase",
        icon = Icons.Filled.Add
    ) {
        override val title: String = "New Case"
    }
//    object CaseList : NavItem(
//        route = "caselist",
//        icon = Icons.Filled.Monitor
//    ) {
//        override val title: String = "Cases"
//    }
    object Profile : NavItem(
        route = "profile",
        icon = Icons.Filled.Person4
    ) {
        override val title: String = "Profile"
    }
}
