package com.dwiaziprasetya.axtro.presentation.addTask

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.dwiaziprasetya.axtro.R
import com.dwiaziprasetya.axtro.core.ui.theme.AxtroTheme
import com.dwiaziprasetya.axtro.core.ui.theme.poppinsFontFamily
import com.dwiaziprasetya.axtro.presentation.component.AxtroCustomDatePicker
import com.dwiaziprasetya.axtro.presentation.component.AxtroDatePickerField
import com.dwiaziprasetya.axtro.presentation.component.AxtroLabeledTextField
import com.dwiaziprasetya.axtro.presentation.component.AxtroPriorityDropdown
import com.dwiaziprasetya.axtro.presentation.component.AxtroTimePicker
import com.dwiaziprasetya.axtro.presentation.component.AxtroTimePickerField
import kotlinx.coroutines.delay
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
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
        startTime = state.startTime,
        endTime = state.endTime,
        onTitleChange = viewModel::onTitleChange,
        onDescriptionChange = viewModel::onDescriptionChange,
        onPriorityChange = viewModel::onPriorityChange,
        onCreateTask = {
            viewModel.createTask()
            focusManager.clearFocus()
        },
        onBackClick = { onNavigateToMain() },
        onEndTimeChange = viewModel::onEndTimeChange,
        onStartTimeChange = viewModel::onStartTimeChange,
        onDateChange = viewModel::onDateChange
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
    startTime: LocalTime?,
    endTime: LocalTime?,
    onStartTimeChange: (LocalTime) -> Unit,
    onEndTimeChange: (LocalTime) -> Unit,
    onTitleChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onPriorityChange: (String) -> Unit,
    onDateChange: (LocalDate) -> Unit,
    onCreateTask: () -> Unit
) {
    var showDatePicker by remember { mutableStateOf(false) }
    var selectedDateText by remember { mutableStateOf("DD/MM/YYYY") }
    var showTimePicker by remember { mutableStateOf(false) }
    var currentTimeType by remember { mutableStateOf<TimeType?>(null) }

    val formatter = DateTimeFormatter.ofPattern("hh:mm a")

    val dateText = state.date?.format(
        DateTimeFormatter.ofPattern("dd MMM yyyy")
    ) ?: "DD/MM/YYYY"
    val startTimeText = startTime?.format(formatter) ?: "HH:mm"
    val endTimeText = endTime?.format(formatter) ?: "HH:mm"

    if (showDatePicker) {
        Dialog(onDismissRequest = { showDatePicker = false }) {
            AxtroCustomDatePicker(
                onDateSelected = { localDate ->
                    val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.getDefault())
                    selectedDateText = localDate.format(formatter)

                    onDateChange(localDate)

                    showDatePicker = false
                },
                onDismiss = { showDatePicker = false }
            )
        }
    }


    if (showTimePicker && currentTimeType != null) {
        Dialog(onDismissRequest = { showTimePicker = false }) {
            AxtroTimePicker(
                onTimeSelected = { h, m, p ->

                    val hour24 = when {
                        p == "PM" && h != 12 -> h + 12
                        p == "AM" && h == 12 -> 0
                        else -> h
                    }

                    val localTime = LocalTime.of(hour24, m)

                    when (currentTimeType) {
                        TimeType.START -> onStartTimeChange(localTime)
                        TimeType.END -> onEndTimeChange(localTime)
                        null -> {}
                    }

                    showTimePicker = false
                },
                onDismiss = { showTimePicker = false }
            )
        }
    }

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
                    text = "Select Date",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp,
                )
                Spacer(Modifier.height(4.dp))
                AxtroDatePickerField(
                    selectedDate = dateText,
                    onDateSelected = { showDatePicker = true }
                )
                Spacer(Modifier.height(16.dp))
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
                            time = startTimeText,
                            onTimePickerClick = {
                                currentTimeType = TimeType.START
                                showTimePicker = true
                            }
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
                            time = endTimeText,
                            onTimePickerClick = {
                                currentTimeType = TimeType.END
                                showTimePicker = true
                            }
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
                    selectedOption = selectedPriority,
                    onOptionSelected = onPriorityChange
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
    }
}

enum class TimeType {
    START, END
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
private fun AddTaskContentPreview() {
    AxtroTheme {
        AddTaskContent(
            state = AddTaskUiState() ,
            onBackClick = {} ,
            title = "" ,
            selectedPriority = "" ,
            onTitleChange = {} ,
            onPriorityChange = {} ,
            onCreateTask = {} ,
            description = "" ,
            onDescriptionChange = {} ,
            startTime = TODO() ,
            endTime = TODO() ,
            onStartTimeChange = TODO() ,
            onEndTimeChange = TODO() ,
            onDateChange = TODO() ,
        )
    }
}