package com.example.axtro.presentation.home

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.axtro.R
import com.example.axtro.core.ui.theme.AxtroTheme
import com.example.axtro.core.util.DateUtils
import com.example.axtro.presentation.component.AxtroAnimatedShimmerCircle
import com.example.axtro.presentation.component.AxtroAnimatedShimmerTaskCard
import com.example.axtro.presentation.component.AxtroAnimatedShimmerText
import com.example.axtro.presentation.component.AxtroEmptyTaskState
import com.example.axtro.presentation.component.AxtroTaskCard
import com.example.axtro.presentation.component.LogoutBottomSheet
import com.example.axtro.presentation.component.StatTaskCard
import com.example.axtro.presentation.navigation.model.Screen
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val systemUiController = rememberSystemUiController()
    val state by viewModel.state.collectAsState()
    var showLogoutSheet by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        systemUiController.setSystemBarsColor(
            color = Color.Transparent,
            darkIcons = true
        )
    }

    LaunchedEffect(state.isLogoutSuccess) {
        if (state.isLogoutSuccess) {
            navController.navigate(Screen.AuthNav.route) {
                popUpTo(Screen.Home.route) {
                    inclusive = true
                }
            }
            viewModel.resetSuccess()
        }
    }

    if (showLogoutSheet) {
        LogoutBottomSheet(
            isLoading = state.isLogoutLoading,
            onConfirm = {
                viewModel.logoutUser()
            },
            onCancel = {
                showLogoutSheet = false
            }
        )
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                shape = RoundedCornerShape(50),
                containerColor = MaterialTheme.colorScheme.primary,
                onClick = {
                    navController.navigate(Screen.AddTask.route)
                }
            ) {
                Icon(
                    painter = painterResource(R.drawable.icon_plus),
                    tint = MaterialTheme.colorScheme.background,
                    contentDescription = null
                )
            }
        }
    ) { _ ->
        HomeContent(
            state = state,
            onCheckedChange = { taskId, isChecked ->
                viewModel.updateTaskStatus(taskId, isChecked)
            },
            onDeletedClick = { taskId ->
                viewModel.removeTask(taskId)
            },
            onUserProfileClick = {
                showLogoutSheet = true
            }
        )
    }
}

@Composable
fun HomeContent(
    state: HomeUiState,
    onCheckedChange: (String, Boolean) -> Unit,
    onDeletedClick: (String) -> Unit,
    onUserProfileClick: () -> Unit
) {
    val displayName = remember(state.userName, state.email) {
        if (!state.userName.isNullOrBlank()) {
            state.userName
        } else {
            state.email.substringBefore("@")
        }
    }
    var selectedChip by remember { mutableStateOf("All") }
    val activeCount = remember(state.tasks) {
        state.tasks.count { it.status == "ACTIVE" }
    }
    val completedCount = remember(state.tasks) {
        state.tasks.count { it.status == "COMPLETED" }
    }
    val filteredTasks = when (selectedChip) {
        "Active" -> state.tasks.filter { it.status == "ACTIVE" }
        "Completed" -> state.tasks.filter { it.status == "COMPLETED" }
        else -> state.tasks
    }

    Box(
        modifier = Modifier
            .padding(
                start = 16.dp,
                top = 8.dp,
                end = 16.dp,
            )
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
            .background(Color(0XFFf2f6fc))
    ){
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    if (state.isUserLoading) {
                        AxtroAnimatedShimmerText(width = 120.dp)
                        Spacer(Modifier.height(8.dp))
                        AxtroAnimatedShimmerText(width = 160.dp)
                    } else {
                        Text(
                            text = "Hi $displayName",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold,
                        )
                        Text(
                            text = "Let’s get things done today",
                            fontSize = 12.sp
                        )
                    }
                }
                if (state.isUserLoading) {
                    AxtroAnimatedShimmerCircle()
                } else {
                    if (state.userPhotoUrl.isNullOrBlank()) {
                        Image(
                            imageVector = Icons.Filled.AccountCircle,
                            contentDescription = "profile picture",
                            colorFilter = ColorFilter.tint(color =
                                MaterialTheme.colorScheme.outline),
                            contentScale = ContentScale.FillBounds,
                            modifier = Modifier
                                .size(40.dp)
                                .clip(RoundedCornerShape(16.dp))
                                .clickable { onUserProfileClick() }
                        )
                    } else {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(state.userPhotoUrl)
                                .crossfade(enable = true)
                                .build(),
                            contentDescription = "User photo profile",
                            placeholder = painterResource(R.drawable.image_placholder),
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .clickable { onUserProfileClick() }
                        )
                    }
                }
            }
            Spacer(Modifier.height(16.dp))
            Row {
                StatTaskCard(
                    modifier = Modifier.weight(1f),
                    value = activeCount.toString(),
                    type = "Active",
                    icon = R.drawable.icon_task
                )
                Spacer(Modifier.width(16.dp))
                StatTaskCard(
                    modifier = Modifier.weight(1f),
                    value = completedCount.toString(),
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
                            .fillMaxWidth()
                            .weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        AxtroEmptyTaskState()
                    }
                }
                else -> {
                    LazyColumn(
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

@Preview(showBackground = true, backgroundColor = 0xFFf2f6fc)
@Composable
private fun HomeContentPreview() {
    AxtroTheme {
        HomeContent(
            state = HomeUiState(),
            onCheckedChange = {_, _ ->},
            onDeletedClick = {_ ->},
            onUserProfileClick = {}
        )
    }
}