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

        if (priority.isBlank()) {
            return AppResult.Error("Priority must be selected")
        }

        if (endTime != null && endTime < startTime) {
            return AppResult.Error("End time must be after start time")
        }

        return repository.addTask(
            title = title.trim(),
            description = description.trim(),
            date = date,
            startTime = startTime,
            endTime = endTime,
            priority = priority
        )
    }
}