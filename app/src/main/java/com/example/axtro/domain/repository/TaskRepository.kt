package com.example.axtro.domain.repository

import com.example.axtro.core.util.AppResult
import com.example.axtro.domain.model.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {

    suspend fun addTask(
        title: String,
        date: Long,
        priority: String
    ): AppResult<Unit>

    fun getTasks() : Flow<List<Task>>
}