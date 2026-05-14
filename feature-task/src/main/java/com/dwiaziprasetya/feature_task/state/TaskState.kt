package com.dwiaziprasetya.feature_task.state

import com.dwiaziprasetya.api_task.domain.model.Task

data class TaskState(
    val tasks: List<Task> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)