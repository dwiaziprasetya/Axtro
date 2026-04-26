package com.dwiaziprasetya.axtro.presentation.task

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dwiaziprasetya.axtro.core.util.DateUtils
import com.dwiaziprasetya.axtro.presentation.component.AxtroAnimatedShimmerTaskCard
import com.dwiaziprasetya.axtro.presentation.component.AxtroEmptyTaskState
import com.dwiaziprasetya.axtro.presentation.component.AxtroTaskCard

@Composable
fun TaskScreen(
    viewModel: TaskViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    TaskContent(
        state = state,
        onCheckedChange = { taskId, isChecked ->
            viewModel.updateTaskStatus(taskId, isChecked)
        },
        onDeletedClick = { taskId ->
            viewModel.removeTask(taskId)
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskContent(
    modifier: Modifier = Modifier,
    state: TaskState,
    onCheckedChange: (String, Boolean) -> Unit,
    onDeletedClick: (String) -> Unit,
) {
    var selectedChip by remember { mutableStateOf("All") }
    val filteredTasks = when (selectedChip) {
        "Active" -> state.tasks.filter { it.status == "ACTIVE" }
        "Completed" -> state.tasks.filter { it.status == "COMPLETED" }
        else -> state.tasks
    }
    val (emptyTitle, emptyDesc) = when (selectedChip) {
        "Active" -> "No active tasks" to "You're all caught up 🎉"
        "Completed" -> "No completed tasks" to "Complete a task to see it here"
        else -> "No tasks yet" to "Start by adding your first task"
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Task",
                        style = MaterialTheme.typography.headlineSmall
                    )
                } ,
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(
                    start = 24.dp,
                    top = 8.dp,
                    end = 24.dp,
                )
                .fillMaxSize()
                .padding(padding) ,
        ) {
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
                                style = MaterialTheme.typography.bodyMedium
                            )
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            containerColor = Color.Transparent,
                            selectedContainerColor = MaterialTheme.colorScheme.primary,
                            selectedLabelColor = Color.White,
                        )
                    )
                }
            }
            Spacer(Modifier.height(8.dp))
            when {
                state.isLoading -> {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(10) {
                            AxtroAnimatedShimmerTaskCard()
                        }
                    }
                }
                filteredTasks.isEmpty() -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        AxtroEmptyTaskState(
                            title = emptyTitle,
                            description = emptyDesc
                        )
                    }
                }
                else -> {
                    LazyColumn(
                        contentPadding = PaddingValues(bottom = 20.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(
                            items = filteredTasks,
                            key = { it.id }
                        ) { task ->
                            AxtroTaskCard(
                                status = task.status,
                                title = task.title,
                                priority = task.priority,
                                date = DateUtils.formatDate(task.date),
                                isChecked = task.status == "COMPLETED",
                                onCheckedChange = { isChecked ->
                                    onCheckedChange(task.id, isChecked)
                                },
                                onDeleteClick = {
                                    onDeletedClick(task.id)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}