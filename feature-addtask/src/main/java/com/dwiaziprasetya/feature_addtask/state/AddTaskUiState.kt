package com.dwiaziprasetya.feature_addtask.state

import java.time.LocalDate
import java.time.LocalTime

data class AddTaskUiState(
    val title: String = "" ,
    val description: String = "" ,
    val date: LocalDate? = null ,
    val startTime: LocalTime? = null ,
    val endTime: LocalTime? = null ,
    val priority: String = "Low" ,
    val isLoading: Boolean = false ,
    val isSuccess: Boolean = false ,
    val error: String? = null
)