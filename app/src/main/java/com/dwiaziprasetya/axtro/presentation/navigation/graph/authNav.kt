package com.dwiaziprasetya.axtro.presentation.navigation.graph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.dwiaziprasetya.axtro.presentation.main.MainScreen
import com.dwiaziprasetya.axtro.presentation.navigation.animation.animatedComposable
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
        animatedComposable(Screen.SignIn.route) {
            SignInScreen(navController = navController)
        }
        animatedComposable(Screen.SignUp.route) {
            SignUpScreen(navController = navController)
        }
    }
}