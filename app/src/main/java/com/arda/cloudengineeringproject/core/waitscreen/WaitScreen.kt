package com.arda.cloudengineeringproject.core.waitscreen

import android.app.Activity
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.arda.cloudengineeringproject.R
import com.arda.core_api.util.DebugTagsEnumUtils
import com.arda.core_ui.nav.NavItem

private val TAG = DebugTagsEnumUtils.UITag.tag


@Composable
fun WaitScreen(
    navController: NavHostController,
    state: WaitScreenUiState,
) {
    Box(
        modifier = Modifier
            .fillMaxSize(1f)
            .background(Color(0xFFEDD1B0))
    ) {
        ConstraintLayout(modifier = Modifier.fillMaxSize(1f)) {
            val (appIcon) = createRefs()
            Image(
                modifier = Modifier
                    .fillMaxHeight(0.25f)
                    .aspectRatio(1f)
                    .graphicsLayer {
                        rotationZ = 0f//it
                    }
                    .constrainAs(appIcon) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    },
                painter = painterResource(R.drawable.splash_icon),
                contentScale = ContentScale.FillBounds,
                contentDescription = "bg"
            )
        }
    }
    val configuration = LocalConfiguration.current

    val activity = LocalContext.current as Activity
    when (configuration.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> {
            Log.v(TAG, "ORIENTED TO LANDSCAPE")
        }

        else -> {
            Log.v(TAG, "ORIENTED TO PORTRAIT")
        }
    }
    val navigatedPlace by rememberUpdatedState(newValue = state.navigatedScreen)
    LaunchedEffect(navigatedPlace, configuration.orientation) {
        Log.v(
            TAG,
            "WAIT SCREEN NAV TRIGGERED WITH ${navigatedPlace}"
        )
        when (navigatedPlace) {
            PossibleNavigations.NAN -> {

            }

            PossibleNavigations.AUTH_SCREEN -> {
                    navController.navigate(NavItem.Auth.route) {
                        popUpTo(NavItem.Auth.route)
                    }
            }

            PossibleNavigations.HOME_SCREEN -> {
                navController.navigate(NavItem.Home.route) {
                    popUpTo(NavItem.Home.route)
                }
            }
        }
    }

}
