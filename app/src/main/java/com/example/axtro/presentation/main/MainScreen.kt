package com.example.axtro.presentation.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.axtro.presentation.navigation.graph.MainNavGraph
import com.example.axtro.core.ui.theme.ListifyTheme
import com.example.axtro.presentation.component.BottomNavigation

@Composable
fun MainScreen(navController: NavHostController = rememberNavController()) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        modifier = Modifier
            .fillMaxSize(),
        bottomBar = {
            BottomNavigation(
                navController = navController,
            )
        }
    ) { innerPadding ->
        MainNavGraph(
            navController = navController,
            paddingValues = innerPadding
        )
    }
}

@Preview
@Composable
private fun MainScreenPreview() {
    ListifyTheme(dynamicColor = false) {
        MainScreen()
    }
}