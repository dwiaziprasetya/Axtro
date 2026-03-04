package com.example.axtro.presentation.home

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.axtro.R
import com.example.axtro.core.ui.theme.ListifyTheme
import com.example.axtro.presentation.component.AxtroCheckbox
import com.example.axtro.presentation.component.StatTaskCard

@Composable
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
fun HomeScreen() {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                shape = RoundedCornerShape(50),
                containerColor = MaterialTheme.colorScheme.primary,
                onClick = {}
            ) {
                Icon(
                    painter = painterResource(R.drawable.icon_plus),
                    tint = MaterialTheme.colorScheme.background,
                    contentDescription = null
                )
            }
        }
    ) { _ ->
        HomeContent()
    }
}

@Composable
fun HomeContent() {
    var selectedChip by remember { mutableStateOf("All") }
    var checked by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .padding(
                start = 16.dp,
                end = 16.dp,
                top = 16.dp
            )
            .fillMaxSize()
            .background(Color(0XFFf2f6fc))
    ){
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "Hi Jhonny",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                    )
                    Text(
                        text = "Let’s get things done today 👋",
                        fontSize = 12.sp
                    )
                }
                Image(
                    painter = painterResource(R.drawable.jhonny),
                    contentDescription = "User",
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                )
            }
            Spacer(Modifier.height(16.dp))
            Row {
                StatTaskCard(
                    modifier = Modifier.weight(1f),
                    value = "10",
                    type = "Active",
                    icon = R.drawable.icon_task
                )
                Spacer(Modifier.width(16.dp))
                StatTaskCard(
                    modifier = Modifier.weight(1f),
                    value = "5",
                    type = "Completed",
                    icon = R.drawable.icon_checklist
                )
            }
            Spacer(Modifier.height(16.dp))
            Text(
                text = "Task",
                fontSize = 30.sp,
                fontWeight = FontWeight.SemiBold,
            )
            Spacer(Modifier.height(8.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf("All", "Active", "Completed").forEach { chip ->
                    FilterChip(
                        selected = selectedChip == chip,
                        onClick = { selectedChip = chip },
                        border = BorderStroke(0.dp, Color.Transparent),
                        label = {
                            Text(
                                text = chip,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium
                            )
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            containerColor = Color.White,
                            selectedContainerColor = MaterialTheme.colorScheme.primary,
                            selectedLabelColor = Color.White,
                            labelColor = Color.Black,
                        )
                    )
                }
            }
            Spacer(Modifier.height(8.dp))
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(10) {
                    TaskCard(
                        isChecked = checked,
                        onCheckedChange = { checked = it }
                    )
                }
            }
        }
    }
}

@Composable
fun TaskCard(
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp)
    ) {

        Row(
            verticalAlignment = Alignment.Top
        ) {

            AxtroCheckbox(
                checked = isChecked,
                onCheckedChange = onCheckedChange
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = "ACTIVE",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(12.dp))

                HorizontalDivider(
                    thickness = 1.dp,
                    color = MaterialTheme.colorScheme.outlineVariant
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Box(
                        modifier = Modifier
                            .width(4.dp)
                            .height(64.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(MaterialTheme.colorScheme.primary)
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = "Prepare Investor Pitch",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Due 12 Mar 2026",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.outline
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFf2f6fc)
@Composable
private fun HomeContentPreview() {
    ListifyTheme {
        HomeContent()
    }
}