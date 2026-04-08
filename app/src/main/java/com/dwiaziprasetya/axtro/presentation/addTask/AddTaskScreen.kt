package com.dwiaziprasetya.axtro.presentation.addTask

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.anhaki.picktime.PickHourMinute
import com.anhaki.picktime.utils.PickTimeFocusIndicator
import com.anhaki.picktime.utils.PickTimeTextStyle
import com.anhaki.picktime.utils.TimeFormat
import com.dwiaziprasetya.axtro.R
import com.dwiaziprasetya.axtro.core.ui.theme.AxtroTheme
import com.dwiaziprasetya.axtro.core.ui.theme.poppinsFontFamily
import com.dwiaziprasetya.axtro.presentation.component.AxtroCustomDatePicker
import com.dwiaziprasetya.axtro.presentation.component.AxtroDatePickerField
import com.dwiaziprasetya.axtro.presentation.component.AxtroLabeledTextField
import com.dwiaziprasetya.axtro.presentation.component.AxtroPriorityDropdown
import com.dwiaziprasetya.axtro.presentation.component.AxtroTimePickerField
import kotlinx.coroutines.delay
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskScreen(
    onNavigateToMain: () -> Unit,
    viewModel: AddTaskViewModel = hiltViewModel()
) {
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
        title = state.title,
        description = state.description,
        selectedPriority = state.priority,
        day = state.day ?: 0,
        month = state.month ?: 0,
        year = state.year ?: 0,
        onTitleChange = viewModel::onTitleChange,
        onDescriptionChange = viewModel::onDescriptionChange,
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

@SuppressLint("DefaultLocale")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddTaskContent(
    state: AddTaskUiState,
    onBackClick: () -> Unit,
    title: String,
    description: String,
    selectedPriority: String,
    day: Int,
    month: Int,
    year: Int,
    onTitleChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onDayChange: (Int) -> Unit,
    onMonthChange: (Int) -> Unit,
    onYearChange: (Int) -> Unit,
    onPriorityChange: (String) -> Unit,
    onCreateTask: () -> Unit
) {
    var input2 by remember { mutableStateOf("") }
    var input5 by remember { mutableStateOf("") }

    var selected by remember { mutableStateOf("Low") }

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
                AxtroLabeledTextField(
                    label = "Title",
                    hint = "Enter your title",
                    text = title,
                    onTextChange = onTitleChange,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(16.dp))
                AxtroLabeledTextField(
                    label = "Description",
                    hint = "Enter your description",
                    text = description,
                    isSingleLine = false,
                    onTextChange = onDescriptionChange,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(16.dp))
                Text(
                    text = "Select Priority",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp,
                )
                Spacer(Modifier.height(4.dp))
                var showDatePicker by remember { mutableStateOf(false) }
                var selectedDateText by remember { mutableStateOf("DD/MM/YYYY") }
                if (showDatePicker) {
                    Dialog(onDismissRequest = { showDatePicker = false }) {
                        AxtroCustomDatePicker(
                            onDateSelected = { localDate ->
                                val formatter = java.time.format.DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.getDefault())
                                selectedDateText = localDate.format(formatter)

                                onDayChange(localDate.dayOfMonth)
                                onMonthChange(localDate.monthValue)
                                onYearChange(localDate.year)

                                showDatePicker = false
                            },
                            onDismiss = { showDatePicker = false }
                        )
                    }
                }
                AxtroDatePickerField(
                    selectedDate = selectedDateText,
                    onDateSelected = { showDatePicker = true }
                )
                Spacer(Modifier.height(16.dp))
                var showTimePicker by remember { mutableStateOf(false) }
                var timeText by remember { mutableStateOf("HH:mm") }
                var timeText2 by remember { mutableStateOf("HH:mm") }
                if (showTimePicker) {
                    Dialog(onDismissRequest = { showTimePicker = false }) {
                        AxtroTimePicker(
                            onTimeSelected = { h, m, p ->
                                timeText = String.format("%02d:%02d %s", h, m, p)
                                showTimePicker = false
                            },
                            onDismiss = { showTimePicker = false }
                        )
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(32.dp)
                ) {
                    Column(
                        modifier = Modifier.weight(1f),
                    ) {
                        Text(
                            text = "Start Time",
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 14.sp,
                        )
                        Spacer(Modifier.height(4.dp))
                        AxtroTimePickerField(
                            time = timeText,
                            onTimePickerClick = { showTimePicker = true }
                        )
                    }
                    Column(
                        modifier = Modifier.weight(1f),
                    ) {
                        Text(
                            text = "End Time",
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 14.sp,
                        )
                        Spacer(Modifier.height(4.dp))
                        AxtroTimePickerField(
                            time = timeText2,
                            onTimePickerClick = { showTimePicker = true }
                        )
                    }
                }
                Spacer(Modifier.height(16.dp))
                Text(
                    text = "Select Priority",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp,
                )
                Spacer(Modifier.height(4.dp))
                AxtroPriorityDropdown(
                    selectedOption = selected,
                    onOptionSelected = { selected = it }
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
fun AxtroTimePicker(
    onTimeSelected: (hour: Int, minute: Int, period: String) -> Unit,
    onDismiss: () -> Unit
) {
    var hour by remember { mutableIntStateOf(3) }
    var minute by remember { mutableIntStateOf(12) }
    var period by remember { mutableStateOf("AM") }

    Surface(
        shape = RoundedCornerShape(24.dp),
        color = Color.White,
        modifier = Modifier.width(300.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Select time",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                PickHourMinute(
                    initialHour = hour,
                    onHourChange = { hour = it },
                    initialMinute = minute,
                    onMinuteChange = { minute = it },
                    timeFormat = TimeFormat.HOUR_24,
                    selectedTextStyle = PickTimeTextStyle(
                        fontWeight = FontWeight.SemiBold
                    ),
                    focusIndicator = PickTimeFocusIndicator(
                        enabled = true,
                        widthFull = false,
                        background = Color.Transparent,
                        shape = RoundedCornerShape(16.dp),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
                    )
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TextButton(onClick = onDismiss) {
                    Text("Cancel", color = Color.Black)
                }
                Button(
                    onClick = { onTimeSelected(hour, minute, period) },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2D5CFF)),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.height(48.dp).width(120.dp)
                ) {
                    Text("Apply", color = Color.White)
                }
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
private fun AddTaskContentPreview() {
    AxtroTheme {
        AddTaskContent(
            state = AddTaskUiState(),
            onBackClick = {},
            title = "",
            selectedPriority = "",
            day = 0,
            month = 0,
            year = 0,
            onTitleChange = {},
            onDayChange = {},
            onMonthChange = {},
            onYearChange = {},
            onPriorityChange = {},
            onCreateTask = {},
            description = "",
            onDescriptionChange = {}
        )
    }
}