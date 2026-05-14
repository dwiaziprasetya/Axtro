package com.dwiaziprasetya.api_task.domain.usecase

import com.dwiaziprasetya.api_task.domain.model.Task
import com.dwiaziprasetya.api_task.domain.repository.TaskRepository
import com.dwiaziprasetya.core_common.util.AppResult
import kotlinx.coroutines.flow.Flow

class GetTasks(
    private val repository: TaskRepository
) {
    operator fun invoke(): Flow<AppResult<List<Task>>> {
        return repository.getTasks()
    }
}