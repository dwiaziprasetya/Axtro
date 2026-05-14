package com.dwiaziprasetya.core_navigation.graph

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.dwiaziprasetya.core_navigation.animation.tabComposable
import com.dwiaziprasetya.core_navigation.model.Screen

@Composable
fun MainNavGraph(
    navController: NavHostController,
    rootController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Task.route,
        route = Screen.Main.route,
    ) {
        tabComposable(route = Screen.Home.route) {
            HomeScreen(
                onNavigateToAuth = {
                    rootController.navigate(Screen.AuthNav.route) {
                        popUpTo(Screen.Main.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        tabComposable(route = Screen.Task.route) {
            TaskScreen()
        }
        tabComposable(route = Screen.Profile.route) {
            ProfileScreen()
        }
        tabComposable(route = Screen.Calendar.route) {
            CalendarScreen()
        }
    }
}