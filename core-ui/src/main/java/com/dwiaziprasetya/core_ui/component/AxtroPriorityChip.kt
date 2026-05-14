package com.dwiaziprasetya.core_ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dwiaziprasetya.axtro.core.util.getPriorityColor

@Composable
fun AxtroPriorityChip(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val isDarkTheme = isSystemInDarkTheme()
    val colors = getPriorityColor(label)

    val containerColor = if (isDarkTheme) colors.selected else colors.container
    val selectedColor = if (isDarkTheme) colors.container else colors.selected

    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(12.dp),
        color = containerColor,
        border = if (isSelected) BorderStroke(3.dp, selectedColor) else null,
        modifier = Modifier.height(30.dp)
    ) {
        Box(
            modifier = Modifier.padding(horizontal = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                fontSize = 12.sp,
                text = label,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.SemiBold,
                    color = if (isSelected) selectedColor else selectedColor.copy(alpha = 0.8f)
                )
            )
        }
    }
}