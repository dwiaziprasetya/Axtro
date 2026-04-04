package com.dwiaziprasetya.axtro.presentation.main

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.dwiaziprasetya.axtro.R
import com.dwiaziprasetya.axtro.core.ui.theme.AxtroTheme
import com.dwiaziprasetya.axtro.presentation.component.BottomNavigation
import com.dwiaziprasetya.axtro.presentation.navigation.graph.MainNavGraph
import com.dwiaziprasetya.axtro.presentation.navigation.model.Screen

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(rootController: NavHostController) {

    val mainNavController = rememberNavController()

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding(),
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                BottomNavigation(
                    navController = mainNavController,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = {
            FloatingActionButton(
                elevation = FloatingActionButtonDefaults.elevation(0.dp),
                shape = RoundedCornerShape(50),
                modifier = Modifier
                    .size(65.dp)
                    .offset(y = 75.dp),
                containerColor = MaterialTheme.colorScheme.primary,
                onClick = {
                    rootController.navigate(Screen.AddTask.route)
                }
            ) {
                Icon(
                    painter = painterResource(R.drawable.icon_plus),
                    tint = Color.White,
                    contentDescription = null
                )
            }
        }
    ) { padding ->
        MainNavGraph(
            navController = mainNavController,
            rootController = rootController,
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