package com.dwiaziprasetya.feature_calendar.component

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dwiaziprasetya.core_ui.R
import com.dwiaziprasetya.core_ui.component.StatusBadge
import com.dwiaziprasetya.core_ui.model.StatusType
import com.dwiaziprasetya.core_ui.theme.priorityHigh
import com.dwiaziprasetya.core_ui.util.DateUtils
import com.dwiaziprasetya.core_ui.util.getPriorityColor
import com.dwiaziprasetya.core_ui.util.toFormattedTimeString
import com.dwiaziprasetya.feature_calendar.model.TaskCalendarItem
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AxtroTaskCalendarCard(
    modifier: Modifier = Modifier ,
    task: TaskCalendarItem
) {
    Card(
        shape = RoundedCornerShape(10.dp) ,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onPrimary
        ) ,
        modifier = modifier
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 1.dp
        )
    ) {
        var expanded by remember { mutableStateOf(false) }
        val scope = rememberCoroutineScope()

        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween ,
                verticalAlignment = Alignment.CenterVertically ,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically ,
                ) {
                    Surface(
                        shape = RoundedCornerShape(10.dp) ,
                        color = getPriorityColor(task.priority).container
                    ) {
                        Box(
                            Modifier.padding(8.dp)
                        ) {
                            Text(
                                text = task.priority ,
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = Color.White
                                )
                            )
                        }
                    }
                    Spacer(Modifier.width(8.dp))
                    StatusBadge(
                        status = task.statusType
                    )
                }
                Box {
                    Icon(
                        imageVector = Icons.Default.MoreVert ,
                        contentDescription = null ,
                        tint = Color.LightGray ,
                        modifier = Modifier.clickable {
                            expanded = true
                        }
                    )
                    DropdownMenu(
                        expanded = expanded ,
                        onDismissRequest = { expanded = false } ,
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.onPrimary)
                    ) {
                        Column {
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = if (task.statusType == StatusType.ACTIVE) {
                                            "Mark as completed"
                                        } else {
                                            "Set as active"
                                        } ,
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                } ,
                                leadingIcon = {
                                    Icon(
                                        painter = painterResource(
                                            if (task.statusType == StatusType.ACTIVE) {
                                                R.drawable.icon_mark_as_completed
                                            } else {
                                                R.drawable.icon_refresh
                                            }
                                        ) ,
                                        contentDescription = null ,
                                        modifier = Modifier.size(20.dp) ,
                                        tint = if (task.statusType == StatusType.ACTIVE) {
                                            Color(0xFF12B76A)
                                        } else {
                                            MaterialTheme.colorScheme.primary
                                        }
                                    )
                                } ,
                                onClick = {
                                    scope.launch {
                                        expanded = false
                                        delay(100)
                                    }
                                }
                            )
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = "Delete" ,
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                } ,
                                leadingIcon = {
                                    Icon(
                                        painter = painterResource(R.drawable.icon_trash) ,
                                        contentDescription = null ,
                                        modifier = Modifier.size(20.dp) ,
                                        tint = priorityHigh
                                    )
                                } ,
                                onClick = {
                                    scope.launch {
                                        expanded = false
                                        delay(100)
                                    }
                                }
                            )
                        }
                    }
                }
            }
            Spacer(Modifier.height(16.dp))
            Text(
                text = task.title ,
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontSize = 14.sp ,
                    fontWeight = FontWeight.SemiBold
                )
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = task.description ,
                style = MaterialTheme.typography.bodySmall ,
                overflow = TextOverflow.Ellipsis ,
                maxLines = 2
            )
            Spacer(Modifier.height(16.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically ,
                horizontalArrangement = Arrangement.SpaceBetween ,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(R.drawable.icon_clock) ,
                        contentDescription = null
                    )
                    Spacer(Modifier.width(10.dp))
                    Text(
                        "${task.startTime.toFormattedTimeString()} - ${task.endTime.toFormattedTimeString()}" ,
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically ,
                ) {
                    Icon(
                        painter = painterResource(R.drawable.icon_calendar_outlined) ,
                        contentDescription = null
                    )
                    Spacer(Modifier.width(10.dp))
                    Text(
                        DateUtils.formatDate(task.date),
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                }
            }
        }
    }
}