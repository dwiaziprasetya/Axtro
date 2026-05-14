package com.dwiaziprasetya.axtro.presentation.task

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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dwiaziprasetya.core_ui.component.AxtroTaskCardNew

@Composable
fun TaskScreen(
    viewModel: TaskViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    TaskContent(
        state = state ,
        onCheckedChange = { taskId , isChecked ->
            viewModel.updateTaskStatus(taskId , isChecked)
        } ,
        onDeletedClick = { taskId ->
            viewModel.removeTask(taskId)
        } ,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskContent(
    modifier: Modifier = Modifier ,
    state: TaskState ,
    onCheckedChange: (String , Boolean) -> Unit ,
    onDeletedClick: (String) -> Unit ,
) {
    var selectedChip by remember { mutableStateOf("All") }
    val filteredTasks = when (selectedChip) {
        "Active" -> state.tasks.filter { it.status == "ACTIVE" }
        "Completed" -> state.tasks.filter { it.status == "COMPLETED" }
        else -> state.tasks
    }
    val (emptyTitle , emptyDesc) = when (selectedChip) {
        "Active" -> "No active tasks" to "You're all caught up 🎉"
        "Completed" -> "No completed tasks" to "Complete a task to see it here"
        else -> "No tasks yet" to "Start by adding your first task"
    }

    Scaffold(
        modifier = modifier ,
//        topBar = {
//            TopAppBar(
//                title = {
//                    Text(
//                        text = "Task",
//                        style = MaterialTheme.typography.headlineSmall
//                    )
//                } ,
//                colors = TopAppBarDefaults.topAppBarColors(
//                    containerColor = Color.White
//                )
//            )
//        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(
                    start = 16.dp ,
                    top = 8.dp ,
                    end = 16.dp ,
                )
                .fillMaxSize()
                .padding(padding) ,
        ) {
//            Row(
//                horizontalArrangement = Arrangement.spacedBy(8.dp)
//            ) {
//                listOf("All", "Active", "Completed").forEach { chip ->
//                    FilterChip(
//                        selected = selectedChip == chip,
//                        onClick = { selectedChip = chip },
//                        border = BorderStroke(0.dp, Color.Transparent),
//                        label = {
//                            Text(
//                                text = chip,
//                                style = MaterialTheme.typography.bodyMedium
//                            )
//                        },
//                        colors = FilterChipDefaults.filterChipColors(
//                            containerColor = Color.Transparent,
//                            selectedContainerColor = MaterialTheme.colorScheme.primary,
//                            selectedLabelColor = Color.White,
//                        )
//                    )
//                }
//            }
//            Spacer(Modifier.height(8.dp))
//            when {
//                state.isLoading -> {
//                    LazyColumn(
//                        verticalArrangement = Arrangement.spacedBy(12.dp)
//                    ) {
//                        items(10) {
//                            AxtroAnimatedShimmerTaskCard()
//                        }
//                    }
//                }
//                filteredTasks.isEmpty() -> {
//                    Box(
//                        modifier = Modifier
//                            .fillMaxSize(),
//                        contentAlignment = Alignment.Center
//                    ) {
//                        AxtroEmptyTaskState(
//                            title = emptyTitle,
//                            description = emptyDesc
//                        )
//                    }
//                }
//                else -> {
//                    LazyColumn(
//                        contentPadding = PaddingValues(bottom = 20.dp),
//                        verticalArrangement = Arrangement.spacedBy(12.dp)
//                    ) {
//                        items(
//                            items = filteredTasks,
//                            key = { it.id }
//                        ) { task ->
//                            AxtroTaskCard(
//                                status = task.status,
//                                title = task.title,
//                                priority = task.priority,
//                                date = DateUtils.formatDate(task.date),
//                                isChecked = task.status == "COMPLETED",
//                                onCheckedChange = { isChecked ->
//                                    onCheckedChange(task.id, isChecked)
//                                },
//                                onDeleteClick = {
//                                    onDeletedClick(task.id)
//                                }
//                            )
//                        }
//                    }
//                }
//            }
            Text(
                text = "Let's your daily task",
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(Modifier.height(16.dp))
            Row(
                modifier = Modifier
                    .shadow(
                        elevation = 1.dp,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .padding(horizontal = 8.dp)
            ) {
                TaskFilterChips()
            }
            Spacer(Modifier.height(8.dp))
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(20) {
                    AxtroTaskCardNew()
                }
            }
        }
    }
}

@Composable
fun TaskFilterChips() {
    val filters = listOf("All" , "Running" , "Completed")
    var selectedFilter by remember { mutableStateOf("All") }

    Row(
        modifier = Modifier.fillMaxWidth() ,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        filters.forEach { filter ->
            FilterChip(
                modifier = Modifier.weight(1f) ,
                selected = selectedFilter == filter ,
                onClick = { selectedFilter = filter } ,
                label = {
                    Text(
                        text = filter ,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Normal
                        ),
                        modifier = Modifier.fillMaxWidth() ,
                        textAlign = TextAlign.Center
                    )
                } ,
                shape = RoundedCornerShape(10.dp) ,
                colors = FilterChipDefaults.filterChipColors(
                    containerColor = Color.Transparent ,
                    selectedContainerColor = MaterialTheme.colorScheme.primary ,
                    selectedLabelColor = Color.White ,
                ) ,
                border = null
            )
        }
    }
}

@Composable
fun StatusBadge(
    status: StatusType
) {
    val (text, bgColor, dotColor, textColor) = when (status) {
        StatusType.RUNNING -> Quadruple(
            "Running",
            Color(0xFFFFF4CC),
            Color(0xFFFFC107),
            Color(0xFFB78103)
        )

        StatusType.COMPLETED -> Quadruple(
            "Completed",
            Color(0xFFD1FADF),
            Color(0xFF12B76A),
            Color(0xFF027A48)
        )
    }

    Row(
        modifier = Modifier
            .background(bgColor, RoundedCornerShape(6.dp))
            .padding(horizontal = 10.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(6.dp)
                .background(dotColor, CircleShape)
        )

        Spacer(modifier = Modifier.width(6.dp))

        Text(
            text = text,
            color = textColor,
            style = MaterialTheme.typography.bodySmall.copy(
                fontWeight = FontWeight.SemiBold
            )
        )
    }
}

enum class StatusType {
    RUNNING,
    COMPLETED
}

data class Quadruple<A, B, C, D>(
    val first: A,
    val second: B,
    val third: C,
    val fourth: D
)