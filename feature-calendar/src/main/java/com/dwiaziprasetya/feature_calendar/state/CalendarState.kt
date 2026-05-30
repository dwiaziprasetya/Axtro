package com.dwiaziprasetya.feature_calendar.state

import android.os.Build
import androidx.annotation.RequiresApi
import com.dwiaziprasetya.api_task.domain.model.Task
import com.dwiaziprasetya.feature_calendar.model.TaskCalendarItem
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
data class CalendarState(
    val selectedDate: LocalDate = LocalDate.now(),
    val tasks: List<TaskCalendarItem> = emptyList(),
    val originalTasks: List<Task> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)