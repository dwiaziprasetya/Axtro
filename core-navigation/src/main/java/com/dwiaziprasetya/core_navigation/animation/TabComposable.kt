package com.dwiaziprasetya.core_navigation.animation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

fun NavGraphBuilder.tabComposable(
    route: String,
    content: @Composable () -> Unit
) {
    composable(
        route = route,
        enterTransition = {
            fadeIn(tween(200)) + scaleIn(
                initialScale = 0.95f,
                animationSpec = tween(200)
            )
        },
        exitTransition = {
            fadeOut(tween(200)) + scaleOut(
                targetScale = 1.05f,
                animationSpec = tween(200)
            )
        },
        popEnterTransition = {
            fadeIn(tween(200)) + scaleIn(
                initialScale = 0.95f,
                animationSpec = tween(200)
            )
        },
        popExitTransition = {
            fadeOut(tween(200)) + scaleOut(
                targetScale = 1.05f,
                animationSpec = tween(200)
            )
        }
    ) {
        content()
    }
}