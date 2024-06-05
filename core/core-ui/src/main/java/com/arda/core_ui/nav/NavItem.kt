package com.arda.dystherapy.nav

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ConnectedTv
import androidx.compose.material.icons.filled.HistoryEdu
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Monitor
import androidx.compose.material.icons.filled.Person4
import androidx.compose.ui.graphics.vector.ImageVector
import com.arda.dystherapy.util.ResourceProvider
import com.arda.dystherapy.validation.StringResourceEnum


sealed class NavItem(
    val route: String,
    val icon: ImageVector
) {
    open val title: String
        get() {
            return ""
        }
    object WaitScreen : NavItem(
        route = "wait",
        icon = Icons.Filled.Key
    )
    object Auth : NavItem(
        route = "auth",
        icon = Icons.Filled.Key
    ) {
        override val title: String
            get() {
                return ResourceProvider(StringResourceEnum.AUTH)
            }
    }
    object Home : NavItem(
        route = "child/home",
        icon = Icons.Filled.Home
    ) {
        override val title: String
            get() {
                return ResourceProvider(StringResourceEnum.HOME)
            }
    }
    object Story : NavItem(
        route = "child/story",
        icon = Icons.Filled.HistoryEdu
    ) {
        override val title: String
            get() {
                return ResourceProvider(StringResourceEnum.STORY)
            }
    }
    object StoryHome : NavItem(
        route = "child/storyhome",
        icon = Icons.Filled.HistoryEdu
    ) {
        override val title: String
            get() {
                return ResourceProvider(StringResourceEnum.STORY_HOME)
            }
    }
    object GameMain : NavItem(
        route = "child/gamemain",
        icon = Icons.Filled.ConnectedTv
    ) {
        override val title: String
            get() {
                return ResourceProvider(StringResourceEnum.GAME_MAIN)
            }
    }
    object GameTheme : NavItem(
        route = "child/gametheme",
        icon = Icons.Filled.ConnectedTv
    ) {
        override val title: String
            get() {
                return ResourceProvider(StringResourceEnum.GAME_MAIN)
            }
    }
    object LevelDecision : NavItem(
        route = "child/leveldecision",
        icon = Icons.Filled.ConnectedTv
    ) {
        override val title: String
            get() {
                return ResourceProvider(StringResourceEnum.GAME_MAIN)
            }
    }
    object Reports : NavItem(
        route = "reports",
        icon = Icons.Filled.Monitor
    ) {
        override val title: String
            get() {
                return ResourceProvider(StringResourceEnum.REPORTS)
            }
    }
    object Profile : NavItem(
        route = "profile",
        icon = Icons.Filled.Person4
    ) {
        override val title: String
            get() {
                return ResourceProvider(StringResourceEnum.PROFILE)
            }
    }
    object Survey : NavItem(
        route = "parent/survey",
        icon = Icons.Filled.Person4
    ) {
        override val title: String
            get() {
                return ResourceProvider(StringResourceEnum.SURVEY)
            }
    }
}
