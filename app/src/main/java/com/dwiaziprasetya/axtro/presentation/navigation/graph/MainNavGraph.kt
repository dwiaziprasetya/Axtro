package com.dwiaziprasetya.axtro.presentation.navigation.graph

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.dwiaziprasetya.axtro.presentation.addTask.AddTaskScreen
import com.dwiaziprasetya.axtro.presentation.home.HomeScreen
import com.dwiaziprasetya.axtro.presentation.navigation.animation.animatedComposable
import com.dwiaziprasetya.axtro.presentation.navigation.model.Screen

@Composable
fun MainNavGraph(
    navController: NavHostController,
    rootController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        route = Screen.Main.route,
    ) {
        animatedComposable(route = Screen.AddTask.route) { AddTaskScreen(navController = navController) }
        animatedComposable(route = Screen.Home.route) { HomeScreen(navController = navController, rootController = rootController) }
    }
}