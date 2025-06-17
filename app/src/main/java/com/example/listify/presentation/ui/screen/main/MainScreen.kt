package com.example.listify.presentation.ui.screen.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.listify.presentation.navigation.graph.MainNavGraph
import com.example.listify.presentation.theme.ListifyTheme
import com.example.listify.presentation.ui.component.BottomNavigation

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