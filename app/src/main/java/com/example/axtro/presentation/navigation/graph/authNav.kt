package com.example.axtro.presentation.navigation.graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.axtro.presentation.navigation.model.Screen
import com.example.axtro.presentation.ui.screen.auth.signin.SignInScreen
import com.example.axtro.presentation.ui.screen.auth.signup.SignUpScreen
import com.example.axtro.presentation.ui.screen.welcome.WelcomeScreen

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