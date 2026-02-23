package com.example.axtro.presentation.navigation.graph

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.axtro.presentation.calendar.CalendarScreen
import com.example.axtro.presentation.home.HomeScreen
import com.example.axtro.presentation.navigation.model.Screen
import com.example.axtro.presentation.profile.ProfileScreen
import com.example.axtro.presentation.task.TaskScreen

@Composable
fun MainNavGraph(
    navController: NavHostController,
    paddingValues: PaddingValues
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        route = Screen.Main.route,
        modifier = Modifier.padding(paddingValues)
    ) {
        authNav()
        composable(route = Screen.Home.route) { HomeScreen() }
        composable(route = Screen.Calendar.route) { CalendarScreen() }
        composable(route = Screen.Task.route) { TaskScreen() }
        composable(route = Screen.Profile.route) { ProfileScreen() }
    }
}