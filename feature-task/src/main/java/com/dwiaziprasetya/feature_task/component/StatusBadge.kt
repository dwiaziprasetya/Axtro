package com.dwiaziprasetya.feature_task.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.dwiaziprasetya.feature_task.model.Quadruple
import com.dwiaziprasetya.feature_task.model.StatusType

@Composable
fun StatusBadge(
    status: StatusType
) {
    val (text, bgColor, dotColor, textColor) = when (status) {
        StatusType.RUNNING -> Quadruple(
            "Running",
            Color(0xFFFFF4CC),
            Color(0xFFFFC107),
            Color(0xFFB78103)
        )

        StatusType.COMPLETED -> Quadruple(
            "Completed",
            Color(0xFFD1FADF),
            Color(0xFF12B76A),
            Color(0xFF027A48)
        )
    }

    Row(
        modifier = Modifier
            .background(bgColor, RoundedCornerShape(6.dp))
            .padding(horizontal = 10.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(6.dp)
                .background(dotColor, CircleShape)
        )

        Spacer(modifier = Modifier.width(6.dp))

        Text(
            text = text,
            color = textColor,
            style = MaterialTheme.typography.bodySmall.copy(
                fontWeight = FontWeight.SemiBold
            )
        )
    }
}