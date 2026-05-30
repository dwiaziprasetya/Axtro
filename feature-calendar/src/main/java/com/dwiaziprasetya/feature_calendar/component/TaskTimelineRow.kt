package com.dwiaziprasetya.feature_calendar.component

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dwiaziprasetya.feature_calendar.model.TaskCalendarItem

@SuppressLint("DefaultLocale")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TaskTimelineRow(
    hour: Int,
    tasks: List<TaskCalendarItem>,
    modifier: Modifier = Modifier
) {
    val hasTasks = tasks.isNotEmpty()

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp) ,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Column(
            modifier = Modifier.width(55.dp) ,
            horizontalAlignment = Alignment.Start
        ) {
            val formattedHour = String.format("%02d:00", hour)
            Text(
                text = formattedHour,
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = if (hasTasks) MaterialTheme
                            .colorScheme
                            .onBackground
                        else MaterialTheme
                            .colorScheme
                            .outline
                            .copy(alpha = 0.5f),
                    fontWeight = if (hasTasks) FontWeight.SemiBold else FontWeight.Normal
                ),
            )
        }
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(20.dp) ,
            contentAlignment = Alignment.TopCenter
        ) {
            val trackColor = if (hasTasks) MaterialTheme.colorScheme.primary else MaterialTheme
                .colorScheme
                .outline
                .copy(alpha = 0.5f)

            Canvas(modifier = Modifier.fillMaxSize()) {
                val circleRadius = if (hasTasks) 6.dp.toPx() else 4.dp.toPx()
                val centerOffset = Offset(size.width / 2 , 10.dp.toPx())

                drawCircle(
                    color = trackColor ,
                    radius = circleRadius ,
                    center = centerOffset ,
                    style = if (hasTasks) Stroke(width = 2.dp.toPx()) else androidx.compose.ui.graphics.drawscope.Fill
                )

                drawLine(
                    color = trackColor ,
                    start = Offset(size.width / 2 , centerOffset.y + circleRadius) ,
                    end = Offset(size.width / 2 , size.height) ,
                    strokeWidth = 2.dp.toPx()
                )
            }
        }
        if (hasTasks) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(bottom = 12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                tasks.forEach { task ->
                    AxtroTaskCalendarCard(
                        modifier = Modifier.fillMaxWidth(),
                        task = task
                    )
                }
            }
        } else {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(60.dp)
                    .padding(bottom = 12.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = "No tasks",
                    fontSize = 12.sp,
                    color = MaterialTheme
                        .colorScheme
                        .outline
                        .copy(alpha = 0.5f),
                    fontWeight = FontWeight.Normal
                )
            }
        }
    }
}