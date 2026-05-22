package com.dwiaziprasetya.feature_task.state

import com.dwiaziprasetya.api_task.domain.model.Task
import com.dwiaziprasetya.feature_task.model.SortType

data class TaskState(
    val tasks: List<Task> = emptyList(),
    val originalTasks: List<Task> = emptyList(),
    val selectedSort: SortType = SortType.DATE_ASCENDING,
    val isLoading: Boolean = false,
    val error: String? = null,
)