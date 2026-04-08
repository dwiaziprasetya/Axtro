package com.dwiaziprasetya.axtro.domain.usecase

import com.dwiaziprasetya.axtro.core.util.AppResult
import com.dwiaziprasetya.axtro.domain.repository.TaskRepository

class AddTask(
    private val repository: TaskRepository
) {
    suspend operator fun invoke(
        title: String,
        description: String,
        date: Long,
        startTime: Long,
        endTime: Long?,
        priority: String
    ): AppResult<Unit> {

        if (title.isBlank()) {
            return AppResult.Error("Title cannot be empty")
        }

        return repository.addTask(
            title = title,
            description = description,
            date = date,
            startTime = startTime,
            endTime = endTime,
            priority = priority
        )
    }
}