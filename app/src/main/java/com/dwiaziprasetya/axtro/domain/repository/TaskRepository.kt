package com.dwiaziprasetya.axtro.domain.repository

import com.dwiaziprasetya.axtro.core.util.AppResult
import com.dwiaziprasetya.axtro.domain.model.Task
import kotlinx.coroutines.flow.Flow

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