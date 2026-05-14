package com.dwiaziprasetya.feature_main.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.dwiaziprasetya.axtro.presentation.navigation.graph.MainNavGraph
import com.dwiaziprasetya.core_ui.component.BottomNavigation
import com.dwiaziprasetya.core_ui.theme.AxtroTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(rootController: NavHostController) {

    val mainNavController = rememberNavController()

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        modifier = Modifier
            .fillMaxSize(),
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                BottomNavigation(
                    navController = mainNavController,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        },
//        floatingActionButtonPosition = FabPosition.Center,
//        floatingActionButton = {
//            FloatingActionButton(
//                elevation = FloatingActionButtonDefaults.elevation(0.dp),
//                shape = RoundedCornerShape(50),
//                modifier = Modifier
//                    .size(65.dp)
//                    .offset(y = 75.dp),
//                containerColor = MaterialTheme.colorScheme.primary,
//                onClick = {
//                    rootController.navigate(Screen.AddTask.route)
//                }
//            ) {
//                Icon(
//                    painter = painterResource(R.drawable.icon_plus),
//                    tint = Color.White,
//                    contentDescription = null
//                )
//            }
//        }
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