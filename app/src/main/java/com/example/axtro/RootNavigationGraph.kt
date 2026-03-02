package com.example.axtro

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.axtro.core.util.ObserveAsEvents
import com.example.axtro.core.util.SnackbarController
import com.example.axtro.presentation.main.MainScreen
import com.example.axtro.presentation.navigation.graph.authNav
import com.example.axtro.presentation.navigation.model.Screen
import kotlinx.coroutines.launch

@Composable
fun RootNavigationGraph(navController: NavHostController) {

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    ObserveAsEvents(
        flow = SnackbarController.events,
        snackbarHostState
    ) { event ->
        scope.launch {
            snackbarHostState.currentSnackbarData?.dismiss()

            val result = snackbarHostState.showSnackbar(
                message = event.message,
                actionLabel = event.action?.name,
                duration = SnackbarDuration.Long
            )

            if (result == SnackbarResult.ActionPerformed) {
                event.action?.action?.invoke()
            }
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            route = Screen.Root.route,
            modifier = Modifier.padding(innerPadding),
            startDestination = Screen.AuthNav.route,
        ) {
            authNav()
            composable(
                route = Screen.Main.route
            ) {
                MainScreen()
            }
        }
    }
}