package com.dwiaziprasetya.axtro.presentation.main

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.dwiaziprasetya.axtro.presentation.navigation.graph.MainNavGraph
import com.dwiaziprasetya.axtro.core.ui.theme.AxtroTheme
import com.dwiaziprasetya.axtro.presentation.component.BottomNavigation

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(rootController: NavHostController) {

    val mainNavController = rememberNavController()

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        modifier = Modifier
            .fillMaxSize(),
        bottomBar = {
            BottomNavigation(
                navController = mainNavController,
            )
        }
    ) { _ ->
        MainNavGraph(
            navController = mainNavController,
            rootController = rootController
        )
    }
}

@Preview
@Composable
private fun MainScreenPreview() {
    AxtroTheme(dynamicColor = false) {
        MainScreen(rememberNavController())
    }
}