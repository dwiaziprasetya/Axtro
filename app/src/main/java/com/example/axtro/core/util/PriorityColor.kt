package com.example.axtro.core.util

import androidx.compose.ui.graphics.Color
import com.example.axtro.core.ui.theme.priorityHigh
import com.example.axtro.core.ui.theme.priorityHighContainer
import com.example.axtro.core.ui.theme.priorityLow
import com.example.axtro.core.ui.theme.priorityLowContainer
import com.example.axtro.core.ui.theme.priorityMedium
import com.example.axtro.core.ui.theme.priorityMediumContainer

data class PriorityColor(
    val selected: Color,
    val container: Color
)

fun getPriorityColor(priority: String): PriorityColor {
    return when (priority) {
        "Low" -> PriorityColor(priorityLow, priorityLowContainer)
        "Medium" -> PriorityColor(priorityMedium, priorityMediumContainer)
        "High" -> PriorityColor(priorityHigh, priorityHighContainer)
        else -> PriorityColor(priorityLow, priorityLowContainer)
    }
}