package com.example.axtro.presentation.navigation.graph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.axtro.presentation.signin.SignInScreen
import com.example.axtro.presentation.signup.SignUpScreen
import com.example.axtro.presentation.navigation.model.Screen
import com.example.axtro.presentation.welcome.WelcomeScreen

fun NavGraphBuilder.authNav(
    navController: NavController
){
    navigation(
        startDestination = Screen.SignIn.route,
        route = Screen.AuthNav.route
    ) {
        composable(
            route = Screen.Welcome.route
        ) {
            WelcomeScreen()
        }
        composable(
            route = Screen.SignIn.route
        ) {
            SignInScreen(
                navController = navController
            )
        }
        composable(
            route = Screen.SignUp.route
        ) {
            SignUpScreen(
                navController = navController
            )
        }
    }
}