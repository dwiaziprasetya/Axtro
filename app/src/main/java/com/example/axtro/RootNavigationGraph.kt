package com.example.axtro

import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.axtro.presentation.navigation.graph.authNav
import com.example.axtro.presentation.navigation.model.Screen
import com.example.axtro.presentation.main.MainScreen

@Composable
fun RootNavigationGraph(navController: NavHostController) {

    val snackbarHostState = remember { SnackbarHostState() }

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