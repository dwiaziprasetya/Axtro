package com.dwiaziprasetya.axtro

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.dwiaziprasetya.core_ui.util.ObserveAsEvents
import com.dwiaziprasetya.core_ui.util.SnackbarController
import com.dwiaziprasetya.core_ui.util.SnackbarEvent
import com.dwiaziprasetya.axtro.presentation.addTask.AddTaskScreen
import com.dwiaziprasetya.axtro.presentation.component.AxtroTopSnackbar
import com.dwiaziprasetya.axtro.presentation.main.MainScreen
import com.dwiaziprasetya.axtro.presentation.navigation.animation.slideComposable
import com.dwiaziprasetya.axtro.presentation.navigation.graph.authNav
import com.dwiaziprasetya.axtro.presentation.navigation.model.Screen
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun RootNavigationGraph(
    navController: NavHostController,
    startDestination: String
) {
    val scope = rememberCoroutineScope()
    var snackbarEvent by remember { mutableStateOf<SnackbarEvent?>(null) }
    var isVisible by remember { mutableStateOf(false) }

    ObserveAsEvents(SnackbarController.events) { event ->
        scope.launch {
            snackbarEvent = event
            isVisible = true
            delay(2500)
            isVisible = false
            delay(300)
            snackbarEvent = null
        }
    }

    Scaffold { _ ->
        Box(modifier = Modifier.fillMaxSize()) {
            NavHost(
                navController = navController,
                route = Screen.Root.route,
                startDestination = startDestination,
            ) {
                authNav(navController)
                slideComposable(route = Screen.Main.route) {
                    MainScreen(rootController = navController)
                }
                slideComposable(route = Screen.AddTask.route) {
                    AddTaskScreen(
                        onNavigateToMain = { navController.popBackStack() }
                    )
                }
            }
            AnimatedVisibility(
                visible = isVisible && snackbarEvent != null,
                enter = slideInVertically(
                    initialOffsetY = { -it }
                ) + fadeIn(),
                exit = slideOutVertically(
                    targetOffsetY = { -it }
                ) + fadeOut(),
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .statusBarsPadding()
            ) {
                snackbarEvent?.let {
                    AxtroTopSnackbar(
                        event = it,
                        onActionClick = {
                            it.action?.action?.invoke()
                        }
                    )
                }
            }
        }
    }
}