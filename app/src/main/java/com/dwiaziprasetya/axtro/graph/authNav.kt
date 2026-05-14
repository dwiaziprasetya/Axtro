package com.dwiaziprasetya.axtro.graph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.dwiaziprasetya.core_navigation.animation.slideComposable
import com.dwiaziprasetya.core_navigation.model.Screen
import com.dwiaziprasetya.feature_signin.screen.SignInScreen
import com.dwiaziprasetya.feature_signup.screen.SignUpScreen

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