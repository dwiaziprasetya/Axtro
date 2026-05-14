package com.dwiaziprasetya.api_task.domain.repository

import com.dwiaziprasetya.api_task.domain.model.Task

interface TaskRepository {

    suspend fun addTask(
        title: String,
        description: String,
        date: Long,
        startTime: Long,
        endTime: Long?,
        priority: String
    ): AppResult<Unit>

    fun getTasks() : Flow<AppResult<List<Task>>>

    suspend fun updateTaskStatus(
        taskId: String,
        status: String
    )

    suspend fun deleteTask(
        taskId: String
    )
}