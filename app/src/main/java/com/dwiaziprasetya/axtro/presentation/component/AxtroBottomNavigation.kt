package com.dwiaziprasetya.axtro.presentation.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.dwiaziprasetya.axtro.R
import com.dwiaziprasetya.axtro.core.ui.theme.AxtroTheme
import com.dwiaziprasetya.axtro.core.ui.theme.poppinsFontFamily
import com.dwiaziprasetya.axtro.presentation.navigation.model.BottomBarItem
import com.dwiaziprasetya.axtro.presentation.navigation.model.Screen

@Composable
fun BottomNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    NavigationBar(
        containerColor = Color.White,
        modifier = modifier
            .fillMaxWidth()
            .shadow(
                elevation = 10.dp,
                shape = RoundedCornerShape(16.dp)
            )
            .clip(RoundedCornerShape(16.dp))
    ) {

        val navBackStackEntry = navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry.value?.destination?.route

        val navigationItems = listOf(
            BottomBarItem(
                "Home",
                painterResource(R.drawable.icon_home_outlined),
                painterResource(R.drawable.icon_home_filled),
                Screen.Home
            ),
            BottomBarItem(
                "Task",
                painterResource(R.drawable.icon_task_outlined),
                painterResource(R.drawable.icon_task_filled),
                Screen.Task
            ),
            BottomBarItem(isDummy = true),
            BottomBarItem(
                "Calendar",
                painterResource(R.drawable.icon_calendar_outlined),
                painterResource(R.drawable.icon_calendar_filled),
                Screen.Calendar
            ),
            BottomBarItem(
                "Profile",
                painterResource(R.drawable.icon_profile_outlined),
                painterResource(R.drawable.icon_profile_filled),
                Screen.Profile
            )
        )

        navigationItems.forEach { item ->

            if (item.isDummy) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                )
            } else {

                val isSelected = currentRoute == item.screen?.route

                NavigationBarItem(
                    selected = isSelected,
                    onClick = {
                        item.screen?.let {
                            navController.navigate(it.route) {
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                                restoreState = true
                                launchSingleTop = true
                            }
                        }
                    },
                    label = {
                        Text(
                            text = item.title,
                            fontFamily = poppinsFontFamily,
                            fontWeight = FontWeight.Medium,
                            color = if (isSelected)
                                MaterialTheme.colorScheme.primary
                            else
                                Color.Black
                        )
                    },
                    icon = {
                        Icon(
                            painter = if (isSelected) item.iconSelected!! else item.icon!!,
                            contentDescription = item.title,
                            tint = if (isSelected)
                                MaterialTheme.colorScheme.primary
                            else
                                Color.Black
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor = Color.Transparent
                    )
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun BottomNavigationPreview() {
    AxtroTheme(dynamicColor = false) {
        BottomNavigation(
            navController = rememberNavController()
        )
    }
}