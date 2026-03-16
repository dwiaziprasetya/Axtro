package com.example.axtro.domain.usecase

import com.example.axtro.domain.model.Task
import com.example.axtro.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow

class GetTasks(
    private val repository: TaskRepository
) {
    operator fun invoke(): Flow<List<Task>> {
        return repository.getTasks()
    }
}