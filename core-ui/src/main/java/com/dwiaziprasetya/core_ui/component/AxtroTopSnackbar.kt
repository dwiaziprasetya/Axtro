package com.dwiaziprasetya.core_ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dwiaziprasetya.core_ui.util.SnackbarEvent
import com.dwiaziprasetya.core_ui.util.SnackbarType

@Composable
fun AxtroTopSnackbar(
    event: SnackbarEvent,
    onActionClick: () -> Unit
) {
    val backgroundColor = when (event.type) {
        SnackbarType.SUCCESS -> Color(0xFF00A547)
        SnackbarType.ERROR -> Color(0xFFD32F2F)
    }

    val icon = when (event.type) {
        SnackbarType.SUCCESS -> Icons.Default.CheckCircle
        SnackbarType.ERROR -> Icons.Default.Warning
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .background(backgroundColor , RoundedCornerShape(10.dp))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null, tint = Color.White)

        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = event.message,
            color = Color.White,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(1f)
        )

        event.action?.let {
            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = it.name,
                color = Color.White,
                modifier = Modifier
                    .clickable { onActionClick() }
            )
        }
    }
}