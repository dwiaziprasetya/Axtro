package com.dwiaziprasetya.api_task.domain.usecase

import com.dwiaziprasetya.axtro.domain.repository.TaskRepository

class DeleteTask(
    private val repository: TaskRepository
) {
    suspend operator fun invoke(
        taskId: String,
    ) {
        repository.deleteTask(taskId)
    }
}


