package com.example.axtro.domain.usecase

import com.example.axtro.domain.repository.TaskRepository

class DeleteTask(
    private val repository: TaskRepository
) {
    suspend operator fun invoke(
        taskId: String,
    ) {
        repository.deleteTask(taskId)
    }
}


