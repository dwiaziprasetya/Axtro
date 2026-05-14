package com.dwiaziprasetya.api_task.domain.usecase

import com.dwiaziprasetya.axtro.domain.repository.TaskRepository

class UpdateTaskStatus(
    private val repository: TaskRepository
) {
    suspend operator fun invoke(
        taskId: String,
        status: String
    ) {
        repository.updateTaskStatus(taskId, status)
    }
}