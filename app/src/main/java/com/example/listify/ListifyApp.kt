package com.example.listify

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.listify.presentation.navigation.graph.authNav
import com.example.listify.presentation.navigation.model.Screen
import com.example.listify.presentation.ui.screen.main.MainScreen
import com.example.listify.presentation.ui.screen.welcome.WelcomeScreen

@Composable
fun ListifyApp(navController: NavHostController) {
    NavHost(
        navController = navController,
        route = Screen.Root.route,
        startDestination = Screen.Main.route,
    ) {
        authNav()
        composable(
            route = Screen.Main.route
        ) {
            MainScreen()
        }
    }
}