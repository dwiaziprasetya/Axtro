package com.dwiaziprasetya.feature_addtask.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import com.dwiaziprasetya.feature_addtask.state.AddTaskUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class AddTaskViewModel @Inject constructor(
    private val addTask: AddTask
) : ViewModel() {

    private val _state = MutableStateFlow(AddTaskUiState())
    val state: StateFlow<AddTaskUiState> = _state

    fun onTitleChange(value: String) {
        _state.update { it.copy(title = value) }
    }

    fun onDescriptionChange(value: String) {
        _state.update { it.copy(description = value) }
    }

    fun onDateChange(date: LocalDate) {
        _state.update { it.copy(date = date) }
    }

    fun onStartTimeChange(time: LocalTime) {
        _state.update { it.copy(startTime = time) }
    }

    fun onEndTimeChange(time: LocalTime) {
        _state.update { it.copy(endTime = time) }
    }

    fun onPriorityChange(value: String) {
        _state.update { it.copy(priority = value) }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun createTask() {
        viewModelScope.launch {
            val current = _state.value

            if (current.title.isBlank()) {
                SnackbarController.sendEvent(
                    SnackbarEvent("Title cannot be empty", SnackbarType.ERROR)
                )
                return@launch
            }

            if (current.date == null) {
                SnackbarController.sendEvent(
                    SnackbarEvent("Date is required", SnackbarType.ERROR)
                )
                return@launch
            }

            if (current.priority.isBlank()) {
                SnackbarController.sendEvent(
                    SnackbarEvent("Priority is required", SnackbarType.ERROR)
                )
                return@launch
            }

            if (current.endTime != null && current.endTime == current.startTime) {
                SnackbarController.sendEvent(
                    SnackbarEvent("End time cannot be the same as start time", SnackbarType.ERROR)
                )
                return@launch
            }

            if (current.startTime == null) {
                SnackbarController.sendEvent(
                    SnackbarEvent("Start time is required", SnackbarType.ERROR)
                )
                return@launch
            }

            if (current.endTime != null && !current.endTime.isAfter(current.startTime)) {
                SnackbarController.sendEvent(
                    SnackbarEvent("End time must be after start time", SnackbarType.ERROR)
                )
                return@launch
            }

            val zone = ZoneId.systemDefault()

            val dateMillis = current.date
                .atStartOfDay(zone)
                .toInstant()
                .toEpochMilli()

            val startMillis = current.date
                .atTime(current.startTime)
                .atZone(zone)
                .toInstant()
                .toEpochMilli()

            val endMillis = current.endTime?.let {
                current.date
                    .atTime(it)
                    .atZone(zone)
                    .toInstant()
                    .toEpochMilli()
            }

            _state.update { it.copy(isLoading = true) }

            val result = addTask(
                title = current.title,
                description = current.description,
                date = dateMillis,
                startTime = startMillis,
                endTime = endMillis,
                priority = current.priority
            )

            when (result) {
                is AppResult.Success -> {
                    _state.update {
                        it.copy(isLoading = false, isSuccess = true)
                    }
                    SnackbarController.sendEvent(
                        SnackbarEvent("Task created", SnackbarType.SUCCESS)
                    )
                }

                is AppResult.Error -> {
                    _state.update {
                        it.copy(isLoading = false, error = result.message)
                    }
                    SnackbarController.sendEvent(
                        SnackbarEvent(result.message, SnackbarType.ERROR)
                    )
                }
            }
        }
    }

    fun resetSuccess() {
        _state.update { it.copy(isSuccess = false) }
    }
}