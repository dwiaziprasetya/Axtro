package com.example.axtro

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.example.axtro.core.ui.theme.AxtroTheme
import com.example.axtro.presentation.navigation.model.Screen
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {

        installSplashScreen()

        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        actionBar?.hide()

        val startDestination =
            if (firebaseAuth.currentUser != null) {
                Screen.Main.route
            } else {
                Screen.AuthNav.route
            }

        setContent {

            val navController = rememberNavController()

            AxtroTheme(dynamicColor = false) {

                RootNavigationGraph(
                    navController = navController,
                    startDestination = startDestination
                )

            }
        }
    }
}