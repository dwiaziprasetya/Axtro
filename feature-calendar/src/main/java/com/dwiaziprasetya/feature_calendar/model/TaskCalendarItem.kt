package com.dwiaziprasetya.feature_calendar.model

import com.dwiaziprasetya.core_ui.model.StatusType

data class TaskCalendarItem(
    val title: String,
    val description: String,
    val statusType: StatusType,
    val startTime: Long,
    val date: Long,
    val endTime: Long,
    val priority: String,
)