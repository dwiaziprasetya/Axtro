package com.dwiaziprasetya.axtro

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.dwiaziprasetya.axtro.core.util.ObserveAsEvents
import com.dwiaziprasetya.axtro.core.util.SnackbarController
import com.dwiaziprasetya.axtro.core.util.SnackbarEvent
import com.dwiaziprasetya.axtro.core.util.SnackbarType
import com.dwiaziprasetya.axtro.presentation.main.MainScreen
import com.dwiaziprasetya.axtro.presentation.navigation.graph.authNav
import com.dwiaziprasetya.axtro.presentation.navigation.model.Screen
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun RootNavigationGraph(
    navController: NavHostController,
    startDestination: String
) {

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val snackbarEvent = remember { mutableStateOf<SnackbarEvent?>(null) }

    ObserveAsEvents(
        flow = SnackbarController.events,
        snackbarHostState
    ) { event ->
        snackbarEvent.value = event
        scope.launch {
            snackbarHostState.currentSnackbarData?.dismiss()

            val result = snackbarHostState.showSnackbar(
                message = event.message,
                actionLabel = event.action?.name,
                duration = SnackbarDuration.Short
            )

            if (result == SnackbarResult.ActionPerformed) {
                event.action?.action?.invoke()
            }
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                val backgroundColor = when (snackbarEvent.value?.type) {
                    SnackbarType.SUCCESS -> Color(0xFF00A547)
                    SnackbarType.ERROR -> Color(0xFFD32F2F)
                    else -> Color.Gray
                }

                Snackbar(containerColor = backgroundColor) {
                    Text(
                        text = it.visuals.message,
                        color = Color.White
                    )
                }
            }
        }
    ) { _  ->
        NavHost(
            navController = navController,
            route = Screen.Root.route,
            startDestination = startDestination,
        ) {
            authNav(navController)
            composable(
                route = Screen.Main.route
            ) {
                MainScreen()
            }
        }
    }
}