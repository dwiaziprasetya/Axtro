package com.example.listify.presentation.navigation.model

import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector

data class BottomBarItem(
    val title: String,
    val icon: Painter,
    val iconSelected: Painter,
    val screen: Screen
)
