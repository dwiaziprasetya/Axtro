package com.dwiaziprasetya.feature_task.component

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dwiaziprasetya.core_ui.R
import com.dwiaziprasetya.core_ui.util.toFormattedTimeString
import com.dwiaziprasetya.feature_task.model.StatusType

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AxtroTaskCardNew(
    modifier: Modifier = Modifier,
    title: String,
    description: String,
    date: String,
    startTime: Long,
    endTime: Long,
    priority: String,
) {
    Card(
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onPrimary
        ) ,
        modifier = modifier
            .fillMaxWidth() ,
        elevation = CardDefaults.cardElevation(
            defaultElevation = 1.dp
        )
    ) {
        Column(
            Modifier.padding(16.dp) ,
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween ,
                verticalAlignment = Alignment.CenterVertically ,
                modifier = Modifier.fillMaxWidth()
            ) {
                StatusBadge(
                    status = StatusType.COMPLETED
                )
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = null,
                    tint = Color.LightGray
                )
            }
            Spacer(Modifier.height(24.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall,
                overflow = TextOverflow.Ellipsis,
                maxLines = 2
            )
            Spacer(Modifier.height(24.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically ,
                horizontalArrangement = Arrangement.SpaceBetween ,
                modifier = Modifier.fillMaxWidth()
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
                        "${startTime.toFormattedTimeString()} - ${endTime.toFormattedTimeString()}" ,
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        painter = painterResource(R.drawable.icon_calendar_outlined) ,
                        contentDescription = null
                    )
                    Spacer(Modifier.width(10.dp))
                    Text(
                        date,
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontWeight = FontWeight.SemiBold
                        )                    )
                }
                Surface(
                    shape = RoundedCornerShape(10.dp) ,
                    color = Color(0XFFE87F24)
                ) {
                    Box(
                        Modifier.padding(8.dp)
                    ) {
                        Text(
                            text = priority,
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = Color.White
                            )
                        )
                    }
                }
            }
        }
    }
}