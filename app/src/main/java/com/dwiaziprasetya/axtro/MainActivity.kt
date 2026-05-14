package com.dwiaziprasetya.axtro

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.dwiaziprasetya.axtro.presentation.navigation.model.Screen
import com.dwiaziprasetya.core_ui.theme.AxtroTheme
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {

        installSplashScreen()

        super.onCreate(savedInstanceState)

        enableEdgeToEdge(
            navigationBarStyle = SystemBarStyle.light(
                android.graphics.Color.TRANSPARENT,
                android.graphics.Color.TRANSPARENT
            )
        )

        actionBar?.hide()

        val startDestination =
            if (firebaseAuth.currentUser != null) {
                Screen.Main.route
            } else {
                Screen.AuthNav.route
            }

        setContent {

            val navController = rememberNavController()
            AxtroTheme {
                RootNavigationGraph(
                    navController = navController,
                    startDestination = startDestination
                )
            }
        }
    }
}