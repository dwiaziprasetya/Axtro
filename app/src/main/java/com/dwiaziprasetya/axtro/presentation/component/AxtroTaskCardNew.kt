package com.dwiaziprasetya.axtro.presentation.component

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dwiaziprasetya.axtro.R
import com.dwiaziprasetya.axtro.presentation.task.StatusBadge
import com.dwiaziprasetya.axtro.presentation.task.StatusType
import com.dwiaziprasetya.core_ui.theme.AxtroTheme

@Composable
fun AxtroTaskCardNew() {
    Card(
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onPrimary
        ) ,
        modifier = Modifier
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
                text = "Design for Task Card" ,
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = "Navigate effortlessly through our site from this central starting point. Effortlessly through our site from this central starting point" ,
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
                        "14:00 - 21:00" ,
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
                        "25 August 2026" ,
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
                            text = "Medium",
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

@Preview(showBackground = true)
@Composable
private fun AxtroTaskCardNewPreview() {
    AxtroTheme {
        AxtroTaskCardNew()
    }
}