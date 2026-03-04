package com.example.axtro.presentation.addTask

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.axtro.core.ui.theme.AxtroTheme
import com.example.axtro.core.ui.theme.poppinsFontFamily
import com.example.axtro.presentation.component.AxtroPriorityChip
import com.example.axtro.presentation.component.AxtroTextField
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskScreen(navController: NavController) {
    val systemUiController = rememberSystemUiController()

    LaunchedEffect(Unit) {
        systemUiController.setSystemBarsColor(
            color = Color.Transparent,
            darkIcons = false
        )
    }
    AddTaskContent(
        onBackClick = {
            navController.popBackStack()
        }
    )
}

@Composable
fun AddTaskContent(
    onBackClick: () -> Unit
) {
    var taskName by remember { mutableStateOf("") }
    var selectedPriority by remember { mutableStateOf("Low") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .windowInsetsPadding(WindowInsets.statusBars)
                    .height(64.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                    contentDescription = "Back",
                    tint = Color.White,
                    modifier = Modifier
                        .size(32.dp)
                        .clickable { onBackClick() }
                )
                Text(
                    text = "Create a Task",
                    color = Color.White,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = Color.White,
                shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp)
                ) {

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.TopStart)
                    ) {
                        Text(
                            text = "Title",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Spacer(Modifier.height(8.dp))
                        AxtroTextField(
                            value = taskName,
                            onValueChange = { taskName = it },
                        )
                        Spacer(Modifier.height(16.dp))
                        Text(
                            text = "Priority",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Spacer(Modifier.height(12.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            AxtroPriorityChip(
                                label = "Low",
                                isSelected = selectedPriority == "Low",
                                selectedColor = Color(0xFF5F5AC9),
                                unselectedBackgroundColor = Color(0xFFE8E7FA),
                                onClick = { selectedPriority = "Low" }
                            )
                            AxtroPriorityChip(
                                label = "Medium",
                                isSelected = selectedPriority == "Medium",
                                selectedColor = Color(0xFFD99011),
                                unselectedBackgroundColor = Color(0xFFFFF4E0),
                                onClick = { selectedPriority = "Medium" }
                            )
                            AxtroPriorityChip(
                                label = "High",
                                isSelected = selectedPriority == "High",
                                selectedColor = Color(0xFFC93E3E),
                                unselectedBackgroundColor = Color(0xFFFDE8E8),
                                onClick = { selectedPriority = "High" }
                            )
                        }
                    }
                    Button(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .height(52.dp)
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp),
                        onClick = {},
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Text(
                            fontFamily = poppinsFontFamily,
                            text = "Create Task",
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.background
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AddTaskContentPreview() {
    AxtroTheme {
        AddTaskContent(
            onBackClick = {}
        )
    }
}