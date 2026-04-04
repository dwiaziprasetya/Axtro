package com.dwiaziprasetya.axtro.presentation.navigation.model

import androidx.compose.ui.graphics.painter.Painter

data class BottomBarItem(
    val title: String = "",
    val icon: Painter? = null,
    val iconSelected: Painter? = null,
    val screen: Screen? = null,
    val isDummy: Boolean = false
)