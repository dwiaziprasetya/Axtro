package com.dwiaziprasetya.api_task.domain.usecase

import com.dwiaziprasetya.api_task.domain.repository.TaskRepository
import com.dwiaziprasetya.core_common.util.AppResult

class AddTask(
    private val repository: TaskRepository
) {
    suspend operator fun invoke(
        title: String,
        description: String,
        date: Long,
        startTime: Long,
        endTime: Long,
        priority: String
    ): AppResult<Unit> {

        if (title.isBlank()) {
            return AppResult.Error("Title cannot be empty")
        }

        if (priority.isBlank()) {
            return AppResult.Error("Priority must be selected")
        }

        if (endTime < startTime) {
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