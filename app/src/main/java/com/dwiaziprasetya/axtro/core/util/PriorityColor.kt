package com.dwiaziprasetya.axtro.core.util

import androidx.compose.ui.graphics.Color
import com.dwiaziprasetya.core_ui.theme.priorityHigh
import com.dwiaziprasetya.core_ui.theme.priorityHighContainer
import com.dwiaziprasetya.core_ui.theme.priorityLow
import com.dwiaziprasetya.core_ui.theme.priorityLowContainer
import com.dwiaziprasetya.core_ui.theme.priorityMedium
import com.dwiaziprasetya.core_ui.theme.priorityMediumContainer

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