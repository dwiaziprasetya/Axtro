package com.example.axtro

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.axtro.presentation.navigation.graph.authNav
import com.example.axtro.presentation.navigation.model.Screen
import com.example.axtro.presentation.main.MainScreen

@Composable
fun RootNavigationGraph(navController: NavHostController) {
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