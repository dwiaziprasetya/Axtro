package com.dwiaziprasetya.feature_calendar.component

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dwiaziprasetya.feature_calendar.model.CalendarDay
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HorizontalCalendar(
    currentMonth: YearMonth,
    days: List<CalendarDay>,
    onDaySelected: (CalendarDay) -> Unit,
    onMonthChanged: (YearMonth) -> Unit,
    modifier: Modifier = Modifier
) {
    val listState = rememberLazyListState()
    val today = LocalDate.now()

    LaunchedEffect(days) {
        val selectedIndex = days.indexOfFirst { it.isSelected }
        if (selectedIndex != -1) {
            listState.animateScrollToItem(selectedIndex)
        }
    }

    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { onMonthChanged(currentMonth.minusMonths(1)) }) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowLeft,
                    contentDescription = "Previous Month",
                    tint = Color.White
                )
            }
            Text(
                text = currentMonth.format(DateTimeFormatter.ofPattern("MMMM yyyy", Locale.getDefault())),
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp,
                    color = Color.White
                ),
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            IconButton(onClick = { onMonthChanged(currentMonth.plusMonths(1)) }) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowRight,
                    contentDescription = "Next Month",
                    tint = Color.White
                )
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        LazyRow(
            state = listState,
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            items(days) { day ->
                val dayOfWeek = day.date.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault())
                val dayOfMonth = day.date.dayOfMonth.toString()

                // DI DALAM ITEMS(DAYS) LAZYROW PADA HORIZONTALCALENDAR.KT

                val isToday = day.date == today

// Logika Pewarnaan Baru yang adaptif di atas background biru gelap
                val containerColor = when {
                    day.isSelected -> Color.White // Saat aktif, dia menjadi putih bersih menonjol
                    isToday -> Color.White.copy(alpha = 0.25f) // Hari ini tapi tidak diklik (putih transparan)
                    else -> Color.White.copy(alpha = 0.1f) // Hari biasa (sangat transparan)
                }

                val textColor = when {
                    day.isSelected -> MaterialTheme.colorScheme.primary // Jika card putih, tulisannya menjadi biru primary
                    isToday -> Color.White
                    else -> Color.White.copy(alpha = 0.9f)
                }

                val subTextColor = when {
                    day.isSelected -> MaterialTheme.colorScheme.primary.copy(alpha = 0.7f)
                    isToday -> Color.White.copy(alpha = 0.7f)
                    else -> Color.White.copy(alpha = 0.5f)
                }

                val cardModifier = Modifier
                    .width(50.dp)
                    .height(70.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(containerColor)
                    // Beri border tipis tambahan untuk hari ini agar lebih kontras terlihat
                    .then(
                        if (isToday && !day.isSelected) {
                            Modifier.border(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.3f), RoundedCornerShape(10.dp))
                        } else Modifier
                    )
                    .clickable { onDaySelected(day) }
                    .padding(vertical = 10.dp)

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = cardModifier
                ) {
                    Text(
                        text = dayOfWeek,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = if (isToday || day.isSelected) FontWeight.Bold else FontWeight.Normal
                        ),
                        color = subTextColor,
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = dayOfMonth,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = if (isToday || day.isSelected) FontWeight.Bold else FontWeight.SemiBold
                        ),
                        color = textColor
                    )
                }
            }
        }
    }
}