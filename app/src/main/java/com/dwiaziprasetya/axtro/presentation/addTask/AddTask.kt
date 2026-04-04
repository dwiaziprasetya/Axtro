package com.dwiaziprasetya.axtro.presentation.addTask

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
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.dwiaziprasetya.axtro.core.ui.theme.AxtroTheme
import com.dwiaziprasetya.axtro.core.ui.theme.poppinsFontFamily
import com.dwiaziprasetya.axtro.presentation.component.AxtroDateInput
import com.dwiaziprasetya.axtro.presentation.component.AxtroPriorityChip
import com.dwiaziprasetya.axtro.presentation.component.AxtroTextField
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskScreen(
    onNavigateToMain: () -> Unit,
    viewModel: AddTaskViewModel = hiltViewModel()
) {
    val systemUiController = rememberSystemUiController()
    val focusManager = LocalFocusManager.current
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        systemUiController.setStatusBarColor(
            color = Color.Transparent,
            darkIcons = false
        )

        systemUiController.setNavigationBarColor(
            color = Color.Transparent,
            darkIcons = true
        )
    }

    LaunchedEffect(state.isSuccess) {
        if (state.isSuccess) {
            delay(1200)
            onNavigateToMain()
            viewModel.resetSuccess()
        }
    }

    AddTaskContent(
        state = state,
        taskName = state.title,
        selectedPriority = state.priority,
        day = state.day ?: 0,
        month = state.month ?: 0,
        year = state.year ?: 0,
        onTitleChange = viewModel::onTitleChange,
        onDayChange = viewModel::onDayChange,
        onMonthChange = viewModel::onMonthChange,
        onYearChange = viewModel::onYearChange,
        onPriorityChange = viewModel::onPriorityChange,
        onCreateTask = {
            viewModel.createTask()
            focusManager.clearFocus()
        },
        onBackClick = { onNavigateToMain() }
    )
}

@Composable
fun AddTaskContent(
    state: AddTaskUiState,
    onBackClick: () -> Unit,
    taskName: String,
    selectedPriority: String,
    day: Int,
    month: Int,
    year: Int,
    onTitleChange: (String) -> Unit,
    onDayChange: (Int) -> Unit,
    onMonthChange: (Int) -> Unit,
    onYearChange: (Int) -> Unit,
    onPriorityChange: (String) -> Unit,
    onCreateTask: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
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
                color = MaterialTheme.colorScheme.background,
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
                            onValueChange = onTitleChange,
                        )
                        Spacer(Modifier.height(24.dp))
                        Text(
                            text = "Set Date",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Spacer(Modifier.height(12.dp))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            AxtroDateInput(
                                hint = "dd",
                                value = if (day == 0) "" else day.toString(),
                                onValueChange = { onDayChange(it.toIntOrNull() ?: 0) },
                                maxChar = 2,
                                modifier = Modifier.size(70.dp, 50.dp)
                            )
                            AxtroDateInput(
                                hint = "mm",
                                value = if (month == 0) "" else month.toString(),
                                onValueChange = { onMonthChange(it.toIntOrNull() ?: 0) },
                                maxChar = 2,
                                modifier = Modifier.size(70.dp, 50.dp)
                            )
                            AxtroDateInput(
                                hint = "yyyy",
                                value = if (year == 0) "" else year.toString(),
                                onValueChange = { onYearChange(it.toIntOrNull() ?: 0) },
                                maxChar = 4,
                                modifier = Modifier.size(100.dp, 50.dp)
                            )
                        }
                        Spacer(Modifier.height(24.dp))
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
                                onClick = { onPriorityChange("Low") }
                            )
                            AxtroPriorityChip(
                                label = "Medium",
                                isSelected = selectedPriority == "Medium",
                                onClick = { onPriorityChange("Medium") }
                            )
                            AxtroPriorityChip(
                                label = "High",
                                isSelected = selectedPriority == "High",
                                onClick = { onPriorityChange("High") }
                            )
                        }
                    }
                    Button(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .height(52.dp)
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp),
                        onClick = onCreateTask,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        if (state.isLoading) {
                            CircularProgressIndicator(
                                color = Color.White,
                                modifier = Modifier.size(18.dp),
                                strokeWidth = 2.dp
                            )
                        } else {
                            Text(
                                fontFamily = poppinsFontFamily,
                                text = "Create Task",
                                fontSize = 14.sp,
                                color = Color.White
                            )
                        }
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
            state = AddTaskUiState(),
            onBackClick = {},
            taskName = "",
            selectedPriority = "",
            day = 0,
            month = 0,
            year = 0,
            onTitleChange = {},
            onDayChange = {},
            onMonthChange = {},
            onYearChange = {},
            onPriorityChange = {},
            onCreateTask = {}
        )
    }
}