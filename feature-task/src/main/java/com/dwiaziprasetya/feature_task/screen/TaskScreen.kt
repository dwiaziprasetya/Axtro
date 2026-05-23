package com.dwiaziprasetya.feature_task.screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dwiaziprasetya.core_ui.R as CoreUiR
import com.dwiaziprasetya.feature_task.R
import com.dwiaziprasetya.core_ui.component.AxtroAnimatedShimmerTaskCard
import com.dwiaziprasetya.core_ui.component.AxtroEmptyTaskState
import com.dwiaziprasetya.core_ui.util.DateUtils
import com.dwiaziprasetya.feature_task.component.AxtroTaskCardNew
import com.dwiaziprasetya.feature_task.component.FilterAndSortBottomSheet
import com.dwiaziprasetya.feature_task.component.TaskFilterChips
import com.dwiaziprasetya.feature_task.model.SortType
import com.dwiaziprasetya.feature_task.model.StatusType
import com.dwiaziprasetya.feature_task.state.TaskState
import com.dwiaziprasetya.feature_task.viewmodel.TaskViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TaskScreen(
    viewModel: TaskViewModel = hiltViewModel(),
    onNavigateToAddTask: () -> Unit
) {
    val systemUiController = rememberSystemUiController()
    val state by viewModel.state.collectAsState()
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    val scope = rememberCoroutineScope()

    var showFilterBottomSheet by remember {
        mutableStateOf(false)
    }

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

    if (showFilterBottomSheet) {
        ModalBottomSheet(
            windowInsets = WindowInsets(0,0,0,0) ,
            sheetState = sheetState,
            onDismissRequest = {
                showFilterBottomSheet = false
            },
            containerColor = Color.White,
            dragHandle = null
        ) {
            FilterAndSortBottomSheet(
                onClose = {
                    scope.launch {
                        sheetState.hide()
                    }.invokeOnCompletion {
                        if (!sheetState.isVisible) {
                            showFilterBottomSheet = false
                        }
                    }
                } ,
                onResetAll = {
                    viewModel.updateSort(SortType.DATE_ASCENDING)
                } ,
                onApply = {

                    scope.launch {

                        sheetState.hide()

                        if (!sheetState.isVisible) {
                            showFilterBottomSheet = false
                        }

                        delay(100)

                        viewModel.updateSort(it)
                    }
                },
                selectedSort = state.selectedSort,
            )
        }
    }

    Content(
        state = state,
        onDeleteTask = viewModel::removeTask,
        onMarkAsCompleted = viewModel::updateTaskStatus,
        onNavigateToAddTask = onNavigateToAddTask,
        onFilterAndSortClick = { showFilterBottomSheet = true }
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun Content(
    modifier: Modifier = Modifier,
    state: TaskState,
    onDeleteTask: (String) -> Unit,
    onMarkAsCompleted: (String, Boolean) -> Unit,
    onNavigateToAddTask: () -> Unit,
    onFilterAndSortClick: () -> Unit,
) {
    val focusManager = LocalFocusManager.current
    var selectedChip by remember { mutableStateOf("All") }
    var searchQuery by remember { mutableStateOf("") }
    val filteredTasks = when (selectedChip) {
        "Active" -> state.tasks.filter { it.status == "ACTIVE" }
        "Completed" -> state.tasks.filter { it.status == "COMPLETED" }
        else -> state.tasks
    }.filter { task ->
        task.title.contains(searchQuery, ignoreCase = true) ||
                task.description.contains(searchQuery, ignoreCase = true)
    }

    val (emptyTitle, emptyDesc) = when {
        searchQuery.isNotEmpty() -> {
            "No results found" to "No tasks found matching \"$searchQuery\""
        }
        selectedChip == "Active" -> "No active tasks" to "You're all caught up 🎉"
        selectedChip == "Completed" -> "No completed tasks" to "Complete a task to see it here"
        else -> "No tasks yet" to "Start by adding your first task"
    }

    val listState = rememberLazyListState()
    val isFabVisible by remember {
        derivedStateOf {
            listState.firstVisibleItemScrollOffset == 0 ||
                    !listState.isScrollInProgress
        }
    }

    LaunchedEffect(listState.isScrollInProgress) {
        if (listState.isScrollInProgress) {
            focusManager.clearFocus()
        }
    }

    val fabAlpha by animateFloatAsState(
        targetValue = if (isFabVisible) 1f else 0f,
        animationSpec = tween(300),
        label = ""
    )

    Scaffold(
        modifier = modifier
            .pointerInput(Unit) {
                detectTapGestures(onTap = { focusManager.clearFocus() })
            },
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            FloatingActionButton(
                shape = RoundedCornerShape(50),
                modifier = Modifier
                    .size(60.dp)
                    .offset(y = (-100).dp)
                    .graphicsLayer {
                        alpha = fabAlpha
                        scaleX = fabAlpha
                        scaleY = fabAlpha
                    },
                elevation = FloatingActionButtonDefaults.elevation(
                    defaultElevation = 0.dp,
                    pressedElevation = 0.dp,
                    focusedElevation = 0.dp,
                    hoveredElevation = 0.dp
                ),
                onClick = onNavigateToAddTask
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null
                )
            }
        },
    ) { padding ->
        Column {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(190.dp)
                    .background(
                        color = MaterialTheme.colorScheme.primary,
                    )
                    .drawBehind {
                        drawCircle(
                            color = Color.White.copy(alpha = 0.08f),
                            radius = 80.dp.toPx(),
                            center = androidx.compose.ui.geometry.Offset(x = size.width * 0.85f, y = size.height * 0.2f)
                        )
                        drawCircle(
                            color = Color.White.copy(alpha = 0.05f),
                            radius = 60.dp.toPx(),
                            center = androidx.compose.ui.geometry.Offset(x = size.width * 0.95f, y = size.height * 0.75f)
                        )
                    }
            ){
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(
                        bottom = 24.dp
                    )
                ) {
                    Text(
                        text = "Hi, Sasha",
                        style = MaterialTheme.typography.headlineLarge,
                        color = Color.White
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = "3 of 5 tasks completed today",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White.copy(alpha = 0.8f)
                    )
                }
            }
            Column(
                modifier = Modifier
                    .offset(y = (-40).dp)
                    .clip(
                        RoundedCornerShape(
                            topStart = 32.dp,
                            topEnd = 32.dp
                        )
                    )
                    .background(
                        color = MaterialTheme.colorScheme.background,
                    )
                    .padding(
                        start = 16.dp,
                        top = 16.dp,
                        end = 16.dp,
                    )
                    .fillMaxSize()
            ) {
                BasicTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    textStyle = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onSurface
                    ),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(
                        onSearch = {
                            focusManager.clearFocus()
                        }
                    ),
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(elevation = 1.dp, shape = RoundedCornerShape(10.dp))
                        .height(44.dp)
                        .background(color = Color.White, shape = RoundedCornerShape(10.dp)),
                    decorationBox = { innerTextField ->
                        Row(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search",
                                tint = Color.Gray,
                                modifier = Modifier.size(20.dp)
                            )
                            Box(modifier = Modifier.weight(1f)) {
                                if (searchQuery.isEmpty()) {
                                    Text(
                                        text = "Search your tasks...",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = Color.Gray.copy(alpha = 0.7f)
                                    )
                                }
                                innerTextField()
                            }
                            if (searchQuery.isNotEmpty()) {
                                androidx.compose.material3.IconButton(
                                    onClick = { searchQuery = "" },
                                    modifier = Modifier.size(24.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Close,
                                        contentDescription = "Clear",
                                        tint = Color.Gray,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                            }
                        }
                    }
                )
                Spacer(Modifier.height(8.dp))
                Row {
                    Row(
                        modifier = Modifier
                            .shadow(
                                elevation = 1.dp,
                                shape = RoundedCornerShape(10.dp)
                            )
                            .height(40.dp)
                            .weight(1f)
                            .background(
                                color = Color.White,
                                shape = RoundedCornerShape(10.dp)
                            )
                            .padding(horizontal = 8.dp)
                    ) {
                        TaskFilterChips(
                            selectedFilter = selectedChip,
                            onFilterSelected = { selectedChip = it }
                        )
                    }
                    Spacer(Modifier.width(8.dp))
                    Surface(
                        modifier = Modifier.size(40.dp),
                        shape = RoundedCornerShape(10.dp),
                        color = Color.White,
                        onClick = onFilterAndSortClick
                    ) {
                        Box(
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = painterResource(CoreUiR.drawable.icon_filter),
                                contentDescription = "Filter"
                            )
                        }
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
                            state = listState,
                            contentPadding = PaddingValues(bottom = 100.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(filteredTasks) { task ->
                                AxtroTaskCardNew(
                                    status = if (task.status == "ACTIVE") StatusType.ACTIVE else StatusType.COMPLETED,
                                    title = task.title,
                                    priority = task.priority,
                                    date = DateUtils.formatDate(task.date),
                                    description = task.description,
                                    startTime = task.startTime,
                                    endTime = task.endTime,
                                    onDeleteTask = { onDeleteTask(task.id) },
                                    onMarkAsCompleted = {
                                        onMarkAsCompleted(task.id, task.status != "COMPLETED")
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}