package com.dwiaziprasetya.core_ui.component

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.dwiaziprasetya.axtro.core.util.getDatesInMonth
import java.time.LocalDate
import java.time.YearMonth
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AxtroCustomDatePicker(
    initialDate: LocalDate = LocalDate.now(),
    onDateSelected: (LocalDate) -> Unit,
    onDismiss: () -> Unit
) {
    val today = LocalDate.now()
    val currentMonthNow = YearMonth.now()

    var currentMonth by remember {
        mutableStateOf(YearMonth.from(initialDate))
    }

    var selectedDate by remember {
        mutableStateOf(
            if (initialDate.isBefore(today)) today else initialDate
        )
    }

    val isCurrentMonth = currentMonth == currentMonthNow

    val daysOfWeek = listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat")
    val dates = remember(currentMonth) { getDatesInMonth(currentMonth) }
    val rows = dates.chunked(7)

    Surface(
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.background,
        tonalElevation = 4.dp,
        modifier = Modifier.width(500.dp)
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = {
                        if (!isCurrentMonth) {
                            currentMonth = currentMonth.minusMonths(1)
                        }
                    },
                    enabled = !isCurrentMonth,
                    modifier = Modifier
                        .border(
                            1.dp,
                            if (isCurrentMonth) Color.LightGray.copy(alpha = 0.3f) else Color.LightGray,
                            CircleShape
                        )
                        .size(36.dp)
                ) {
                    Icon(
                        Icons.AutoMirrored.Filled.KeyboardArrowLeft ,
                        contentDescription = null,
                        tint = if (isCurrentMonth) MaterialTheme
                            .colorScheme
                            .onBackground
                            .copy(alpha = 0.3f)
                        else MaterialTheme
                            .colorScheme
                            .onBackground
                    )
                }
                Text(
                    text = "${currentMonth.month.getDisplayName(
                        java.time.format.TextStyle.FULL,
                        Locale.ENGLISH
                    )} ${currentMonth.year}",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
                IconButton(
                    onClick = { currentMonth = currentMonth.plusMonths(1) },
                    modifier = Modifier
                        .border(1.dp, Color.LightGray, CircleShape)
                        .size(36.dp)
                ) {
                    Icon(
                        Icons.AutoMirrored.Filled.KeyboardArrowRight ,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                daysOfWeek.forEach { day ->
                    Text(
                        text = day,
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            rows.forEach { week ->
                Row(modifier = Modifier.fillMaxWidth()) {
                    week.forEach { date ->

                        val isSelected = date == selectedDate
                        val isCurrentMonthDate = date?.month == currentMonth.month
                        val isPastDate = date?.isBefore(today) == true

                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .aspectRatio(1f)
                                .padding(4.dp)
                                .clip(CircleShape)
                                .background(
                                    if (isSelected) MaterialTheme.colorScheme.primary
                                    else Color.Transparent
                                )
                                .clickable(
                                    enabled = date != null && !isPastDate
                                ) {
                                    date?.let { selectedDate = it }
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            if (date != null) {
                                Text(
                                    text = date.dayOfMonth.toString(),
                                    color = when {
                                        isSelected -> Color.White
                                        isPastDate -> MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
                                        isCurrentMonthDate -> MaterialTheme.colorScheme.onBackground
                                        else -> MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
                                    },
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(onClick = onDismiss) {
                    Text(
                        "Cancel",
                        color = MaterialTheme
                            .colorScheme
                            .onBackground
                    )
                }

                Button(
                    onClick = { onDateSelected(selectedDate) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    ),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .height(48.dp)
                        .width(120.dp)
                ) {
                    Text("Apply", color = Color.White)
                }
            }
        }
    }
}