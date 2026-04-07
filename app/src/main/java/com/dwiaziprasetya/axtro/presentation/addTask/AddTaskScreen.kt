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
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.dwiaziprasetya.axtro.R
import com.dwiaziprasetya.axtro.core.ui.theme.AxtroTheme
import com.dwiaziprasetya.axtro.core.ui.theme.poppinsFontFamily
import com.dwiaziprasetya.axtro.core.util.getDatesInMonth
import com.dwiaziprasetya.axtro.presentation.component.AxtroCustomDatePicker
import com.dwiaziprasetya.axtro.presentation.component.AxtroDatePickerField
import com.dwiaziprasetya.axtro.presentation.component.AxtroLabeledTextField
import com.dwiaziprasetya.axtro.presentation.component.AxtroPriorityDropdown
import kotlinx.coroutines.delay
import java.time.LocalDate
import java.time.YearMonth
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
                                // Format tanggal sesuai keinginan, misal: "07 Apr 2026"
                                val formatter = java.time.format.DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.getDefault())
                                selectedDateText = localDate.format(formatter)

                                // Update ke ViewModel jika perlu
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
//                Row(
//                    modifier = Modifier.fillMaxWidth(),
//                    horizontalArrangement = Arrangement.spacedBy(32.dp)
//                ) {
//                    AxtroLabeledTextField(
//                        label = "Start Time",
//                        hint = "HH:mm",
//                        text = "09:45",
//                        onTextChange = {},
//                        modifier = Modifier.weight(1f),
//                        trailingIconResId = R.drawable.icon_clock
//                    )
//                    AxtroLabeledTextField(
//                        label = "End Time",
//                        hint = "HH:mm",
//                        text = "10:30",
//                        onTextChange = {},
//                        modifier = Modifier.weight(1f),
//                        trailingIconResId = R.drawable.icon_clock
//                    )
//                }
                var showTimePicker by remember { mutableStateOf(false) }
                var timeText by remember { mutableStateOf("09:45 AM") }

                if (showTimePicker) {
                    Dialog(onDismissRequest = { showTimePicker = false }) {
                        CustomTimePicker(
                            onTimeSelected = { h, m, p ->
                                timeText = String.format("%02d:%02d %s", h, m, p)
                                showTimePicker = false
                            },
                            onDismiss = { showTimePicker = false }
                        )
                    }
                }
                AxtroLabeledTextField(
                    label = "Start Time",
                    hint = "HH:mm",
                    text = timeText,
                    onTextChange = {}, // Biarkan kosong karena input dari picker
                    modifier = Modifier.weight(1f).clickable { showTimePicker = true },
                    trailingIconResId = R.drawable.icon_clock
                )
                Spacer(Modifier.height(16.dp))
//                LabeledTextField(
//                    label = "Priority",
//                    hint = "Select Priority",
//                    text = "High",
//                    onTextChange = { input5 = it },
//                    modifier = Modifier.fillMaxWidth(),
//                    trailingIconResId = R.drawable.icon_arrow_down
//                )
                var selected by remember { mutableStateOf("Low") }
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WheelPicker(
    count: Int,
    label: (Int) -> String,
    onItemSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val state = rememberPagerState(initialPage = Int.MAX_VALUE / 2, pageCount = { Int.MAX_VALUE })

    // Sinkronisasi pilihan ke parent
    LaunchedEffect(state.currentPage) {
        onItemSelected(state.currentPage % count)
    }

    VerticalPager(
        state = state,
        modifier = modifier.height(150.dp),
        contentPadding = PaddingValues(vertical = 60.dp), // Membuat item tengah jadi fokus
    ) { page ->
        val index = page % count
        val isSelected = state.currentPage == page

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = label(index),
                style = TextStyle(
                    fontSize = if (isSelected) 20.sp else 16.sp,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                    color = if (isSelected) Color.Black else Color.LightGray
                )
            )
        }
    }
}

@Composable
fun CustomTimePicker(
    onTimeSelected: (hour: Int, minute: Int, period: String) -> Unit,
    onDismiss: () -> Unit
) {
    var selectedHour by remember { mutableStateOf(3) }
    var selectedMinute by remember { mutableStateOf(12) }
    var selectedPeriod by remember { mutableStateOf("AM") }

    Surface(
        shape = RoundedCornerShape(24.dp),
        color = Color.White,
        modifier = Modifier.width(280.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Select time",
                style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 16.sp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Box(contentAlignment = Alignment.Center) {
                // Background Highlight di tengah
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                        .background(Color(0xFFF7F7F7), RoundedCornerShape(8.dp))
                        .border(0.5.dp, Color(0xFFE0E0E0), RoundedCornerShape(8.dp))
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Jam (1-12)
                    WheelPicker(
                        count = 12,
                        label = { String.format("%02d", it + 1) },
                        onItemSelected = { selectedHour = it + 1 },
                        modifier = Modifier.weight(1f)
                    )

                    Text(":", style = TextStyle(fontWeight = FontWeight.Bold))

                    // Menit (0-59)
                    WheelPicker(
                        count = 60,
                        label = { String.format("%02d", it) },
                        onItemSelected = { selectedMinute = it },
                        modifier = Modifier.weight(1f)
                    )

                    // AM/PM
                    val periods = listOf("AM", "PM")
                    WheelPicker(
                        count = 2,
                        label = { periods[it] },
                        onItemSelected = { selectedPeriod = periods[it] },
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(onClick = onDismiss) {
                    Text("Cancel", color = Color.Black, fontWeight = FontWeight.Bold)
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    onClick = { onTimeSelected(selectedHour, selectedMinute, selectedPeriod) },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                    border = BorderStroke(1.dp, Color.LightGray),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Save", color = Color.Black, fontWeight = FontWeight.Bold)
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