package com.dwiaziprasetya.feature_task.component

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.dwiaziprasetya.feature_task.model.Quadruple
import com.dwiaziprasetya.feature_task.model.StatusType

@Composable
fun StatusBadge(
    status: StatusType,
    modifier: Modifier = Modifier
) {
    val (targetText, targetBgColor, targetDotColor, targetTextColor) = when (status) {
        StatusType.ACTIVE -> Quadruple(
            "Active",
            Color(0xFFEDF3FF),
            Color(0xFF407BFF),
            Color(0xFF00298A)
        )

        StatusType.COMPLETED -> Quadruple(
            "Completed",
            Color(0xFFD1FADF),
            Color(0xFF12B76A),
            Color(0xFF027A48)
        )
    }

    val animatedBgColor by animateColorAsState(
        targetValue = targetBgColor,
        animationSpec = tween(durationMillis = 400),
        label = "BgColorAnimation"
    )
    val animatedDotColor by animateColorAsState(
        targetValue = targetDotColor,
        animationSpec = tween(durationMillis = 400),
        label = "DotColorAnimation"
    )
    val animatedTextColor by animateColorAsState(
        targetValue = targetTextColor,
        animationSpec = tween(durationMillis = 400),
        label = "TextColorAnimation"
    )

    Row(
        modifier = modifier
            .background(animatedBgColor, RoundedCornerShape(6.dp))
            .padding(horizontal = 10.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(6.dp)
                .background(animatedDotColor, CircleShape)
        )
        Spacer(modifier = Modifier.width(6.dp))
        AnimatedContent(
            targetState = targetText,
            transitionSpec = {
                fadeIn(animationSpec = tween(durationMillis = 300)) togetherWith
                        fadeOut(animationSpec = tween(durationMillis = 300))
            },
            label = "TextChangeAnimation"
        ) { text ->
            Text(
                text = text,
                color = animatedTextColor,
                style = MaterialTheme.typography.bodySmall.copy(
                    fontWeight = FontWeight.SemiBold
                )
            )
        }
    }
}