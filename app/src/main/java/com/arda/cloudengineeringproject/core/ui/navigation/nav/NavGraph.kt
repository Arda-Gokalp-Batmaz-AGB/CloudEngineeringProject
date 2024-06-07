package com.arda.cloudengineeringproject.core.ui.navigation.nav


import android.app.Activity
import android.util.Log
import android.view.ViewTreeObserver
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardBackspace
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.arda.auth_ui.auth.AuthScreen
import com.arda.auth_ui.auth.AuthViewModel
import com.arda.case_ui.screens.createcase.CreateCase
import com.arda.case_ui.screens.createcase.CreateCaseViewModel
import com.arda.case_ui.screens.userhome.UserHome
import com.arda.case_ui.screens.userhome.UserHomeViewModel
import com.arda.cloudengineeringproject.core.waitscreen.WaitScreen
import com.arda.cloudengineeringproject.core.waitscreen.WaitScreenViewModel
import com.arda.core_api.util.DebugTagsEnumUtils
import com.arda.core_ui.components.SpecialIconButton
import com.arda.core_ui.nav.NavItem
import com.arda.core_ui.theme.ProjectTheme

private val TAG = DebugTagsEnumUtils.UITag.tag

@Composable
fun NavGraph(
    navController: NavHostController,
) {
    val initialRoute: String =
        NavItem.WaitScreen.route//ScreenTypeEnum.CHILD.route//ScreenTypeEnum.WAITSCREEN.route
    NavHost(
        navController = navController,
        startDestination = initialRoute
    )
    {

        composable(route = NavItem.WaitScreen.route) {
            val waitScreenViewModel = hiltViewModel<WaitScreenViewModel>()
            val state by waitScreenViewModel.uiState.collectAsState()
            WaitScreen(
                navController = navController,
                state = state,
            )
        }
        Parentcomposable(route = NavItem.Auth.route, navController = navController)
        {
            val authViewModel = hiltViewModel<AuthViewModel>()
            val state by authViewModel.uiState.collectAsState()
            AuthScreen(authViewModel::onEvent, state, navController)
        }
        Parentcomposable(route = NavItem.Home.route, navController = navController)
        {
            val userHomeViewModel = hiltViewModel<UserHomeViewModel>()
            val state by userHomeViewModel.uiState.collectAsState()
            UserHome(userHomeViewModel::onEvent,state, navController)
        }
        Parentcomposable(route = NavItem.NewCase.route, navController = navController)
        {
            val createCaseViewModel = hiltViewModel<CreateCaseViewModel>()
            val state by createCaseViewModel.uiState.collectAsState()
            CreateCase(createCaseViewModel::onEvent,state, navController)
        }
    }
}

@Composable
fun BackButton(navigateBack: () -> Unit) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxHeight(0.2f)
            .fillMaxWidth(1f)
    ) {
        val guidelineBot = createGuidelineFromTop(0.15f)
        val guidelineLeft = createGuidelineFromStart(0.1f)

        val (backButtonPart) = createRefs()
        Box(modifier = Modifier
//                .fillMaxSize(1f)
            .constrainAs(backButtonPart) {
                start.linkTo(parent.start)
                top.linkTo(parent.top)
                bottom.linkTo(guidelineBot)
                end.linkTo(guidelineLeft)
            }

        ) {
            SpecialIconButton(
                onClick = navigateBack,//{navigateBack.invoke()},
                modifier = Modifier.clip(RoundedCornerShape(20.dp)),
                icon = {
                    Box(
                        modifier = Modifier
                            .fillMaxSize(1f)
                    ) {
                        Icon(
                            modifier = Modifier
                                .padding(0.dp)
                                .matchParentSize()
                                .clip(RoundedCornerShape(20.dp)),
//                            .clip(RoundedCornerShape(20.dp)),
                            imageVector = Icons.Filled.KeyboardBackspace,//resim yüklenmio tipi deişmesi gerekebilr
                            tint = MaterialTheme.colorScheme.onSecondary,
                            contentDescription = null,
                        )
                    }

                },
                colors = IconButtonDefaults.iconButtonColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
            )

        }
    }

}

//@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalLayoutApi::class)
fun NavGraphBuilder.Parentcomposable(
    route: String,
    arguments: List<NamedNavArgument> = emptyList(),
//    topBarData : CustomTopBarData? = null,
    navController: NavHostController,
    content: @Composable (NavBackStackEntry) -> Unit,
) {
    composable(
        route = route, arguments = arguments,
    ) { navBackStackEntry ->
        ProjectTheme() {
            Scaffold(
                modifier = Modifier,//.windowInsetsPadding(WindowInsets.displayCutout).windowInsetsPadding(WindowInsets.safeDrawing)
                bottomBar = {
//                    val keyboardIsVisible by keyboardAsState()//rememberUpdatedState(newValue = WindowInsets.isImeVisible)//daha efektif bir yol olabilir
//                    Log.v(TAG, "Keybord:${keyboardIsVisible} and route =$route")
                    if (route != NavItem.Auth.route)//!keyboardIsVisible &&
                        BottomBar(navController = navController)
                }
            )
            {
                Box(modifier = Modifier.padding(it)) {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        //color = MaterialTheme.colorScheme.background
                    ) {
                        SetDecorFitsSystemWindows(true)

                        content(navBackStackEntry)
                    }
                }
            }

        }
    }
}
@Composable
fun keyboardAsState(): State<Boolean> {
    val view = LocalView.current
    var isImeVisible by remember { mutableStateOf(false) }

    DisposableEffect(LocalWindowInfo.current) {
        val listener = ViewTreeObserver.OnPreDrawListener {
            isImeVisible = ViewCompat.getRootWindowInsets(view)
                ?.isVisible(WindowInsetsCompat.Type.ime()) == true
            true
        }
        view.viewTreeObserver.addOnPreDrawListener(listener)
        onDispose {
            view.viewTreeObserver.removeOnPreDrawListener(listener)
        }
    }
    return rememberUpdatedState(isImeVisible)
}
@Composable
fun BottomBar(navController: NavHostController) {
    val screens = listOf(
        NavItem.Home,
        NavItem.NewCase,
//        NavItem.CaseList
    )
    NavigationBar(modifier = Modifier) {//, contentColor = color
        screens.forEachIndexed { i, screen ->
            navController.currentDestination?.route?.startsWith(screen.route, ignoreCase = true)
                ?.let {
                    AddItem(
                        selected = it,
                        screenData = screen,
                        navController = navController,
                        onClick = {
                            navController.navigate(screen.route) {
                                launchSingleTop = true
                            }
                        }
                    )
                }
        }

    }
}

@Composable
private fun RowScope.AddItem(
    screenData: NavItem,
    navController: NavHostController,
    selected: Boolean,
    onClick: () -> Unit,
) {
    NavigationBarItem(
        selected = selected,
        onClick = onClick,
        label = {
            Text(screenData.title, style = MaterialTheme.typography.titleMedium)
            //Text("Home", style = MaterialTheme.typography.titleMedium)
        },
        alwaysShowLabel = true,
        icon = {
            Icon(
                screenData.icon,
                modifier = Modifier
                    .fillMaxWidth(0.4f)
                    .fillMaxHeight(0.5f),
                contentDescription = "Localized description"
            )
        }
    )

}
//@Composable
//fun NavigationAuthCheckMiddleWare(
//    targetScreen: @Composable () -> Unit,
//    onNavigateToLoginScreen: () -> Unit = {},
//) {
//    val state by loggedCheckState.current.uiState.collectAsState()
//    when (state.loggedFlow) {
//        is Resource.Failure<*> -> {
//            LaunchedEffect(state) {
//                onNavigateToLoginScreen()
//            }
//        }
//
//        is Resource.Sucess<*> -> {
//            targetScreen()
//        }
//
//        Resource.Loading -> {
//            // LoadingView()
//        }
//
//        else -> {
//            return
//        }
//    }
//}
//
//@Composable
//fun ThemeandModeCheckMiddleWare(
//    targetScreen: @Composable () -> Unit,
//    onNavigateToNextScreen: (lastThemeID: Int, lastModeID: Int, lastModeTypeID: Int, lang: LangEnum) -> Unit,
//) {
//    val state by loggedCheckState.current.uiState.collectAsState()
//    if (state.detailedUser!!.lastThemeID == -1 && state.detailedUser!!.lastModeID == -1) {
//        targetScreen()
//    } else {
//        LaunchedEffect(state) {
//            Log.v(TAG,"onNavigateToNextScreen WORKS")
//            onNavigateToNextScreen(
//                state.detailedUser!!.lastThemeID,
//                state.detailedUser!!.lastModeID,
//                state.detailedUser!!.lastModeType,
//                LanguageUtil.currentLanguage,
//            )
//        }
//    }
//}

@Composable
fun SetDecorFitsSystemWindows(set: Boolean) {
    val context = LocalContext.current
    if (context is Activity) {
        WindowCompat.setDecorFitsSystemWindows(context.window, set)
    }
}

fun NavigateParentScreens(targetScreen: @Composable () -> Unit) {

}

//@Composable
//fun NavigationSurveyMiddleWare(
//    targetScreen: @Composable () -> Unit,
//    onNavigateToSurveyScreen: () -> Unit = {},
//) {
//    val state by loggedCheckState.current.uiState.collectAsState()
//    Log.v(TAG, "NavigationSurveyMiddleWare : ${state.detailedUserFlow}")
//    when (state.detailedUserFlow) {
//        is Resource.Failure<*> -> {
//            LaunchedEffect(state) {
//                onNavigateToSurveyScreen()
//            }
//        }
//
//        is Resource.Sucess<*> -> {
//            targetScreen()
//        }
//
//        Resource.Loading -> {
//            // LoadingView()
//        }
//
//        else -> {
//        }
//    }
//}