package com.dwiaziprasetya.axtro.presentation.addTask

import android.R.id.input
import android.graphics.Color.alpha
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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dwiaziprasetya.axtro.R
import com.dwiaziprasetya.axtro.core.ui.theme.AxtroTheme
import com.dwiaziprasetya.axtro.core.ui.theme.poppinsFontFamily
import com.dwiaziprasetya.axtro.presentation.component.AxtroDateInput
import com.dwiaziprasetya.axtro.presentation.component.AxtroPriorityChip
import com.dwiaziprasetya.axtro.presentation.component.AxtroTextField
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.firebase.firestore.pipeline.Expression.Companion.isError
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
    var input by remember { mutableStateOf("") }
    var input2 by remember { mutableStateOf("") }
    var input5 by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
            .background(MaterialTheme.colorScheme.background)
            .padding(
                horizontal = 24.dp,
                vertical = 16.dp
            )
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Create New Task",
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp
                )
                Icon(
                    modifier = Modifier.clickable { onBackClick() },
                    painter = painterResource(R.drawable.icon_cancel),
                    contentDescription = "cancel",
                    tint = MaterialTheme.colorScheme.outline,
                )
            }
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState()),
            ) {
                Spacer(Modifier.height(16.dp))
                LabeledTextField(
                    label = "Title",
                    hint = "Enter your title",
                    text = input,
                    onTextChange = { input = it },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(16.dp))
                LabeledTextField(
                    label = "Description",
                    hint = "Enter your description",
                    text = input2,
                    isSingleLine = false,
                    onTextChange = { input2 = it },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(16.dp))
                LabeledTextField(
                    label = "Date",
                    hint = "dd/MM/yyyy",
                    text = "25/12/2026",
                    onTextChange = {},
                    modifier = Modifier.fillMaxWidth(),
                    trailingIconResId = R.drawable.icon_calendar_outlined
                )
                Spacer(Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(32.dp)
                ) {
                    LabeledTextField(
                        label = "Start Time",
                        hint = "HH:mm",
                        text = "09:45",
                        onTextChange = {},
                        modifier = Modifier.weight(1f),
                        trailingIconResId = R.drawable.icon_clock
                    )
                    LabeledTextField(
                        label = "End Time",
                        hint = "HH:mm",
                        text = "10:30",
                        onTextChange = {},
                        modifier = Modifier.weight(1f),
                        trailingIconResId = R.drawable.icon_clock
                    )
                }
                Spacer(Modifier.height(16.dp))
                LabeledTextField(
                    label = "Priority",
                    hint = "Select Priority",
                    text = "High",
                    onTextChange = { input5 = it },
                    modifier = Modifier.fillMaxWidth(),
                    trailingIconResId = R.drawable.icon_arrow_down
                )
                Spacer(Modifier.height(16.dp))
            }
            Button(
                modifier = Modifier
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
                        text = "Create",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                }
            }
        }
//        Column(modifier = Modifier.fillMaxSize()) {
//            Box(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(horizontal = 16.dp)
//                    .windowInsetsPadding(WindowInsets.statusBars)
//                    .height(64.dp),
//                contentAlignment = Alignment.CenterStart
//            ) {
//                Icon(
//                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
//                    contentDescription = "Back",
//                    tint = Color.White,
//                    modifier = Modifier
//                        .size(32.dp)
//                        .clickable { onBackClick() }
//                )
//                Text(
//                    text = "Create a Task",
//                    color = Color.White,
//                    style = MaterialTheme.typography.titleMedium,
//                    modifier = Modifier.align(Alignment.Center)
//                )
//            }
//            Surface(
//                modifier = Modifier.fillMaxSize(),
//                color = MaterialTheme.colorScheme.background,
//                shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
//            ) {
//                Box(
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .padding(24.dp)
//                ) {
//                    Column(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .align(Alignment.TopStart)
//                    ) {
//                        Text(
//                            text = "Title",
//                            style = MaterialTheme.typography.titleMedium
//                        )
//                        Spacer(Modifier.height(8.dp))
//                        AxtroTextField(
//                            value = taskName,
//                            onValueChange = onTitleChange,
//                        )
//                        Spacer(Modifier.height(24.dp))
//                        Text(
//                            text = "Set Date",
//                            style = MaterialTheme.typography.titleMedium
//                        )
//                        Spacer(Modifier.height(12.dp))
//                        Row(
//                            modifier = Modifier
//                                .fillMaxWidth(),
//                            horizontalArrangement = Arrangement.spacedBy(12.dp)
//                        ) {
//                            AxtroDateInput(
//                                hint = "dd",
//                                value = if (day == 0) "" else day.toString(),
//                                onValueChange = { onDayChange(it.toIntOrNull() ?: 0) },
//                                maxChar = 2,
//                                modifier = Modifier.size(70.dp, 50.dp)
//                            )
//                            AxtroDateInput(
//                                hint = "mm",
//                                value = if (month == 0) "" else month.toString(),
//                                onValueChange = { onMonthChange(it.toIntOrNull() ?: 0) },
//                                maxChar = 2,
//                                modifier = Modifier.size(70.dp, 50.dp)
//                            )
//                            AxtroDateInput(
//                                hint = "yyyy",
//                                value = if (year == 0) "" else year.toString(),
//                                onValueChange = { onYearChange(it.toIntOrNull() ?: 0) },
//                                maxChar = 4,
//                                modifier = Modifier.size(100.dp, 50.dp)
//                            )
//                        }
//                        Spacer(Modifier.height(24.dp))
//                        Text(
//                            text = "Priority",
//                            style = MaterialTheme.typography.titleMedium
//                        )
//                        Spacer(Modifier.height(12.dp))
//                        Row(
//                            modifier = Modifier.fillMaxWidth(),
//                            horizontalArrangement = Arrangement.spacedBy(12.dp)
//                        ) {
//                            AxtroPriorityChip(
//                                label = "Low",
//                                isSelected = selectedPriority == "Low",
//                                onClick = { onPriorityChange("Low") }
//                            )
//                            AxtroPriorityChip(
//                                label = "Medium",
//                                isSelected = selectedPriority == "Medium",
//                                onClick = { onPriorityChange("Medium") }
//                            )
//                            AxtroPriorityChip(
//                                label = "High",
//                                isSelected = selectedPriority == "High",
//                                onClick = { onPriorityChange("High") }
//                            )
//                        }
//                    }
//                    Button(
//                        modifier = Modifier
//                            .align(Alignment.BottomCenter)
//                            .height(52.dp)
//                            .fillMaxWidth(),
//                        shape = RoundedCornerShape(10.dp),
//                        onClick = onCreateTask,
//                        colors = ButtonDefaults.buttonColors(
//                            containerColor = MaterialTheme.colorScheme.primary
//                        )
//                    ) {
//                        if (state.isLoading) {
//                            CircularProgressIndicator(
//                                color = Color.White,
//                                modifier = Modifier.size(18.dp),
//                                strokeWidth = 2.dp
//                            )
//                        } else {
//                            Text(
//                                fontFamily = poppinsFontFamily,
//                                text = "Create Task",
//                                fontSize = 14.sp,
//                                color = Color.White
//                            )
//                        }
//                    }
//                }
//            }
//        }
    }
}


@Composable
fun LabeledTextField(
    label: String,
    text: String,
    onTextChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    hint: String = "",
    isSingleLine: Boolean = true,
    trailingIconResId: Int? = null,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    Column(modifier = modifier) {
        Text(
            text = label,
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp,
        )
        Spacer(Modifier.height(4.dp))
        TextField(
            modifier = Modifier.fillMaxWidth(),
            singleLine = isSingleLine,
            value = text,
            shape = RoundedCornerShape(10.dp),
            onValueChange = { onTextChange(it) },
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = {
                keyboardController?.hide()
                focusManager.clearFocus()
            }),
            placeholder =  {
                Text(
                    text = hint,
                    color = MaterialTheme.colorScheme.outline,
                    fontSize = 14.sp,
                )
            },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                disabledContainerColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            trailingIcon = if (trailingIconResId != null) {
                {
                    Icon(
                        painter = painterResource(trailingIconResId),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.outline,
                    )
                }
            } else null
        )
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