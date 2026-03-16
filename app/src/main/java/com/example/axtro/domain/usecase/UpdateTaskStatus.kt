package com.example.axtro.domain.usecase

import com.example.axtro.domain.repository.TaskRepository

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