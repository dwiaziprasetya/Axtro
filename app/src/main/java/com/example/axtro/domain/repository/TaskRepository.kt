package com.example.axtro.domain.repository

import com.example.axtro.core.util.AppResult

interface TaskRepository {
    suspend fun addTask(
        title: String,
        date: Long,
        priority: String
    ): AppResult<Unit>
}