package com.dwiaziprasetya.axtro.domain.usecase

import com.dwiaziprasetya.axtro.core.util.AppResult
import com.dwiaziprasetya.axtro.domain.repository.TaskRepository

class AddTask(
    private val repository: TaskRepository
) {
    suspend operator fun invoke(
        title: String,
        date: Long,
        priority: String
    ): AppResult<Unit> {

        if (title.isBlank()) {
            return AppResult.Error("Title cannot be empty")
        }

        if (priority.isBlank()) {
            return AppResult.Error("Priority cannot be empty")
        }

        return repository.addTask(
            title = title,
            date = date,
            priority = priority
        )
    }
}