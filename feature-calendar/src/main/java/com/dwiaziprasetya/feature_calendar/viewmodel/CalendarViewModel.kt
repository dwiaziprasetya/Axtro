package com.dwiaziprasetya.feature_calendar.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dwiaziprasetya.api_task.domain.model.Task
import com.dwiaziprasetya.api_task.domain.usecase.DeleteTask
import com.dwiaziprasetya.api_task.domain.usecase.GetTasks
import com.dwiaziprasetya.api_task.domain.usecase.UpdateTaskStatus
import com.dwiaziprasetya.core_common.util.AppResult
import com.dwiaziprasetya.core_ui.model.StatusType
import com.dwiaziprasetya.feature_calendar.model.TaskCalendarItem
import com.dwiaziprasetya.feature_calendar.state.CalendarState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import javax.inject.Inject
import kotlin.collections.filter


@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val getTasks: GetTasks,
    private val deleteTask: DeleteTask,
    private val updateTaskStatus: UpdateTaskStatus
) : ViewModel() {

    private val _state = MutableStateFlow(CalendarState())
    val state: StateFlow<CalendarState> = _state

    init {
        observeTasks()
    }

    private fun observeTasks() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            getTasks().collect { result ->
                when (result) {
                    is AppResult.Success -> {
                        val filteredTasks = filterTasksByDate(
                            tasks = result.data,
                            targetDate = _state.value.selectedDate
                        )

                        _state.update {
                            it.copy(
                                originalTasks = result.data,
                                tasks = filteredTasks,
                                isLoading = false,
                                error = null
                            )
                        }
                    }
                    is AppResult.Error -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                error = result.message
                            )
                        }
                    }
                }
            }
        }
    }

    // Fungsi untuk memperbarui tanggal yang dipilih dari Kalender Horizontal Row
    fun changeSelectedDate(newDate: LocalDate) {
        val filteredTasks = filterTasksByDate(
            tasks = _state.value.originalTasks,
            targetDate = newDate
        )

        _state.update {
            it.copy(
                selectedDate = newDate,
                tasks = filteredTasks
            )
        }
    }

    fun updateTaskStatus(taskId: String, isCompleted: Boolean) {
        viewModelScope.launch {
            val status = if (isCompleted) "COMPLETED" else "ACTIVE"
            updateTaskStatus(taskId, status)
        }
    }

    fun removeTask(taskId: String) {
        viewModelScope.launch {
            deleteTask(taskId)
        }
    }

    // Helper Pemrosesan Data (Filter & Mapping ke UI model)
    private fun filterTasksByDate(tasks: List<Task>, targetDate: LocalDate): List<TaskCalendarItem> {
        return tasks
            .filter { isSameDay(it.date , targetDate) }
            .map { it.toCalendarItem() } // Ekstensi mapper yang kita buat sebelumnya
    }

    private fun isSameDay(taskDateMills: Long, selectedLocalDate: LocalDate): Boolean {
        val taskLocalDate = Instant.ofEpochMilli(taskDateMills)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()
        return taskLocalDate == selectedLocalDate
    }
}

fun Task.toCalendarItem(): TaskCalendarItem {
    return TaskCalendarItem(
        title = this.title ,
        description = this.description ,
        statusType = if (this.status == "COMPLETED") StatusType.COMPLETED else StatusType.ACTIVE,
        startTime = this.startTime ,
        endTime = this.endTime ,
        date = this.date ,
        priority = this.priority
    )
}