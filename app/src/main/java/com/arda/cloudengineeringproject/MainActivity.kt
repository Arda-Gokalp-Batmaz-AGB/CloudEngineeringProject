package com.arda.cloudengineeringproject

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.arda.cloudengineeringproject.core.ui.navigation.nav.NavGraph
import com.arda.core_api.util.DebugTagsEnumUtils
import com.arda.core_ui.components.providers.LocalSnackbarHostState
import com.arda.core_ui.components.providers.ProvideSnackBar
import com.arda.core_ui.components.snackbar.SnackBarShowcase
import com.arda.core_ui.components.snackbar.SnackBarTypeEnum
import com.arda.core_ui.components.snackbar.SnackbarData
import com.arda.core_ui.theme.ProjectTheme
import dagger.hilt.android.AndroidEntryPoint

private val TAG = DebugTagsEnumUtils.UITag.tag

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(),
                //color = MaterialTheme.colorScheme.background
            ) {
                ProvideSnackBar() {
                    ProvideTextStyle(value = MaterialTheme.typography.bodyMedium) {
                        MainScreen()
                    }
                }

            }
//            }
        }
    }
}


@OptIn(ExperimentalLayoutApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val snackbarHostState = LocalSnackbarHostState.current
    val scope = rememberCoroutineScope()
    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState) { data ->
                val receivedSnackbarData =
                    data.visuals as? com.arda.core_ui.components.snackbar.SnackbarData
                Log.v(TAG, "snackbar: ${receivedSnackbarData}")//snackbartypes classı lazım
                Log.v(TAG, "snackbarmessage: ${receivedSnackbarData?.message}")//snackbartypes classı lazım
                if (receivedSnackbarData != null) {
                    ProjectTheme() {
                        SnackBarShowcase(receivedSnackbarData)
                    }
                }
                else{
                    val snackbarData = SnackbarData("Operation Successful!",SnackBarTypeEnum.OPERATION_SUCESS)
                    ProjectTheme() {
                        SnackBarShowcase(snackbarData)
                    }
                }
            }
        },

        )
    {
        Box(
            modifier = Modifier
                .padding(it)
                .background(MaterialTheme.colorScheme.background)
        ) {
            NavGraph(navController = navController)
        }
    }

}


