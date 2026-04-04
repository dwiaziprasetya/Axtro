package com.dwiaziprasetya.axtro.presentation.navigation.graph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.dwiaziprasetya.axtro.presentation.navigation.animation.slideComposable
import com.dwiaziprasetya.axtro.presentation.navigation.model.Screen
import com.dwiaziprasetya.axtro.presentation.signin.SignInScreen
import com.dwiaziprasetya.axtro.presentation.signup.SignUpScreen

fun NavGraphBuilder.authNav(
    navController: NavController
) {
    navigation(
        startDestination = Screen.SignIn.route,
        route = Screen.AuthNav.route
    ) {
        slideComposable(Screen.SignIn.route) {
            SignInScreen(navController = navController)
        }
        slideComposable(Screen.SignUp.route) {
            SignUpScreen(navController = navController)
        }
    }
}