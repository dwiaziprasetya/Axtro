package com.dwiaziprasetya.axtro.core.util

import androidx.compose.ui.graphics.Color
import com.dwiaziprasetya.axtro.core.ui.theme.priorityHigh
import com.dwiaziprasetya.axtro.core.ui.theme.priorityHighContainer
import com.dwiaziprasetya.axtro.core.ui.theme.priorityLow
import com.dwiaziprasetya.axtro.core.ui.theme.priorityLowContainer
import com.dwiaziprasetya.axtro.core.ui.theme.priorityMedium
import com.dwiaziprasetya.axtro.core.ui.theme.priorityMediumContainer

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