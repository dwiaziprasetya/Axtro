package com.dwiaziprasetya.api_task.domain.usecase

import com.dwiaziprasetya.api_task.domain.repository.TaskRepository

class DeleteTask(
    private val repository: TaskRepository
) {
    suspend operator fun invoke(
        taskId: String,
    ) {
        repository.deleteTask(taskId)
    }
}


