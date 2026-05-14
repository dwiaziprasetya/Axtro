package com.dwiaziprasetya.api_task.domain.usecase

import com.dwiaziprasetya.axtro.core.util.AppResult
import com.dwiaziprasetya.axtro.domain.model.Task
import com.dwiaziprasetya.axtro.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow

class GetTasks(
    private val repository: TaskRepository
) {
    operator fun invoke(): Flow<AppResult<List<Task>>> {
        return repository.getTasks()
    }
}