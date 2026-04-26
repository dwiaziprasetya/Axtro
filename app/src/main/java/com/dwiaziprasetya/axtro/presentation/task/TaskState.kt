package com.dwiaziprasetya.axtro.presentation.task

import com.dwiaziprasetya.axtro.domain.model.Task

data class TaskState(
    val tasks: List<Task> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
