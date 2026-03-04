package com.example.axtro.presentation.component

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.axtro.R
import com.example.axtro.presentation.navigation.model.BottomBarItem
import com.example.axtro.presentation.navigation.model.Screen
import com.example.axtro.core.ui.theme.ListifyTheme
import com.example.axtro.core.ui.theme.poppinsFontFamily

@Composable
fun BottomNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.background,
        modifier = modifier
            .drawWithContent {
                drawContent()
                drawLine(
                    color = Color(0xFFE3E3E3),
                    start = Offset(0f, 0f),
                    end = Offset(size.width, 0f),
                    strokeWidth = 2f
                )
            }
    ) {
        val navBackStackEntry = navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry.value?.destination?.route
        val navigationItems = listOf(
            BottomBarItem(
                title = "Home",
                icon = painterResource(R.drawable.icon_home_outlined),
                iconSelected = painterResource(R.drawable.icon_home_filled),
                screen = Screen.Home,
            ),
            BottomBarItem(
                title = "Task",
                icon = painterResource(R.drawable.icon_task),
                iconSelected = painterResource(R.drawable.icon_task),
                screen = Screen.Task,
            ),
            BottomBarItem(
                title = "Calendar",
                icon = painterResource(R.drawable.icon_calendar),
                iconSelected = painterResource(R.drawable.icon_calendar),
                screen = Screen.Calendar,
            ),
            BottomBarItem(
                title = "Profile",
                icon = painterResource(R.drawable.icon_profile_outlined),
                iconSelected = painterResource(R.drawable.icon_profile_filled),
                screen = Screen.Profile,
            )
        )
        navigationItems.map { item ->
            val isSelected = currentRoute == item.screen.route
            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    navController.navigate(item.screen.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        restoreState = true
                        launchSingleTop = true
                    }
                },
                label = {
                    if (isSelected) {
                        Text(
                            text = item.title,
                            fontFamily = poppinsFontFamily,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.primary
                        )
                    } else {
                        Text(
                            text = item.title,
                            fontFamily = poppinsFontFamily,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.outline
                        )
                    }
                },
                icon = {
                    if (isSelected) {
                        Icon(
                            painter = item.iconSelected,
                            contentDescription = item.title,
                            tint = MaterialTheme.colorScheme.primary
                        )
                    } else {
                        if (item.title != "") {
                            Icon(
                                painter = item.icon,
                                tint = MaterialTheme.colorScheme.outline,
                                contentDescription = item.title
                            )
                        }
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = MaterialTheme.colorScheme.background
                ),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun BottomNavigationPreview() {
    ListifyTheme(dynamicColor = false) {
        BottomNavigation(
            navController = rememberNavController()
        )
    }
}