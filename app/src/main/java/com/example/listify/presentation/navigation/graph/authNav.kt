package com.example.listify.presentation.navigation.graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.listify.presentation.navigation.model.Screen
import com.example.listify.presentation.ui.screen.auth.signin.SignInScreen
import com.example.listify.presentation.ui.screen.auth.signup.SignUpScreen
import com.example.listify.presentation.ui.screen.welcome.WelcomeScreen

fun NavGraphBuilder.authNav(){
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
            SignInScreen()
        }
        composable(
            route = Screen.SignUp.route
        ) {
            SignUpScreen()
        }
    }
}