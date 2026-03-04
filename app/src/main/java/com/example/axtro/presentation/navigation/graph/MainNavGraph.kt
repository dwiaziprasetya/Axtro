package com.example.axtro.presentation.navigation.graph

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.axtro.presentation.addTask.AddTaskScreen
import com.example.axtro.presentation.calendar.CalendarScreen
import com.example.axtro.presentation.home.HomeScreen
import com.example.axtro.presentation.navigation.animation.animatedComposable
import com.example.axtro.presentation.navigation.model.Screen
import com.example.axtro.presentation.profile.ProfileScreen
import com.example.axtro.presentation.task.TaskScreen

@Composable
fun MainNavGraph(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        route = Screen.Main.route,
    ) {
        authNav(navController)
        animatedComposable(route = Screen.AddTask.route) { AddTaskScreen(navController = navController) }
        animatedComposable(route = Screen.Home.route) { HomeScreen(navController = navController) }
    }
}