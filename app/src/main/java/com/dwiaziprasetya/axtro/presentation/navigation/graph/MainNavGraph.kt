package com.dwiaziprasetya.axtro.presentation.navigation.graph

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.dwiaziprasetya.axtro.presentation.addTask.AddTaskScreen
import com.dwiaziprasetya.axtro.presentation.calendar.CalendarScreen
import com.dwiaziprasetya.axtro.presentation.home.HomeScreen
import com.dwiaziprasetya.axtro.presentation.navigation.animation.tabComposable
import com.dwiaziprasetya.axtro.presentation.navigation.model.Screen
import com.dwiaziprasetya.axtro.presentation.profile.ProfileScreen
import com.dwiaziprasetya.axtro.presentation.task.TaskScreen

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