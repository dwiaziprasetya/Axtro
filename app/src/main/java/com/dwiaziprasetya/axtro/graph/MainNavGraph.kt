package com.dwiaziprasetya.axtro.graph

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.dwiaziprasetya.core_navigation.animation.tabComposable
import com.dwiaziprasetya.core_navigation.model.Screen
import com.dwiaziprasetya.feature_calendar.screen.CalendarScreen
import com.dwiaziprasetya.feature_home.screen.HomeScreen
import com.dwiaziprasetya.feature_profile.screen.ProfileScreen
import com.dwiaziprasetya.feature_task.screen.TaskScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainNavGraph(
    navController: NavHostController,
    rootController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Calendar.route,
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
            TaskScreen(
                onNavigateToAddTask = { rootController.navigate(Screen.AddTask.route) }
            )
        }
        tabComposable(route = Screen.Profile.route) {
            ProfileScreen()
        }
        tabComposable(route = Screen.Calendar.route) {
            CalendarScreen()
        }
    }
}