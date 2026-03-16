package com.example.axtro.domain.usecase

import com.example.axtro.core.util.AppResult
import com.example.axtro.domain.repository.TaskRepository

class AddTask(
    private val repository: TaskRepository
) {
    suspend operator fun invoke(
        title: String,
        date: Long,
        priority: String
    ): AppResult<Unit> {

        return repository.addTask(
            title = title,
            date = date,
            priority = priority
        )
    }
}